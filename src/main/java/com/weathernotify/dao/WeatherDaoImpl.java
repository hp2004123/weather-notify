package com.weathernotify.dao;

import com.weathernotify.model.MyWeather;
import com.weathernotify.model.Pm25.Pm25;
import com.weathernotify.model.Pm25.Records;
import com.weathernotify.model.Weather.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WeatherDaoImpl implements WeatherDao {


    @Override
    public Pm25 getPm25() {
        String url ="https://data.epa.gov.tw/api/v1/aqx_p_02?api_key=9be7b239-557b-4c10-9775-78cadfc555e9";



        RestTemplate restTemplate = new RestTemplate();

//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("test", "test");
//        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        ResponseEntity<Pm25> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Pm25.class
        );



        Pm25 pm25 = responseEntity.getBody();

      if(pm25 != null) {
          return pm25;
      }
      else {
          return null;
      }


    }

    @Override
    public List<MyWeather> getWeather() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_XML);
        HttpEntity requestEntity = new HttpEntity(requestHeaders);


        String url = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=rdec-key-123-45678-011121314";


        ResponseEntity<Weather> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                Weather.class
        );


        Weather weather = responseEntity.getBody();
        com.weathernotify.model.Weather.Records records = weather.getRecords();
        Result result = weather.getResult();

//        System.out.println("http狀態碼: " + responseEntity.getStatusCode());
//        System.out.println(weather.getSuccess());
//        System.out.println(records.getDatasetDescription());
//        System.out.println(result.getResource_id());


        List<Location> locationList = records.getLocation();

        int counter = 0;
        List<MyWeather> list = new ArrayList();

        for (Location location : locationList) {


            String locationName = location.getLocationName();
            List<WeatherElement> weatherElementList = location.getWeatherElement();

            MyWeather[] myWeatherArr = {new MyWeather(), new MyWeather(), new MyWeather()};


            for (WeatherElement weatherElement : weatherElementList) {//5 count

                String elementName = weatherElement.getElementName();

                List<Time> timeList = weatherElement.getTime();


                for (Time time : timeList) {//3 count

                    myWeatherArr[counter].setLocationName(locationName);

                    Parameter parameter = time.getParameter();

                    switch (elementName) {


                        case "Wx":

                            myWeatherArr[counter].setStartTime(time.getStartTime());
                            myWeatherArr[counter].setEndTime(time.getEndTime());


                            myWeatherArr[counter].setSkyImgNumber(Integer.parseInt(parameter.getParameterValue()));
                            myWeatherArr[counter].setSky(parameter.getParameterName());
                            break;

                        case "PoP":
                            myWeatherArr[counter].setRain(Integer.parseInt(parameter.getParameterName()));
                            break;

                        case "MinT":
                            myWeatherArr[counter].setMinT(Integer.parseInt(parameter.getParameterName()));
                            break;

                        case "MaxT":
                            myWeatherArr[counter].setMaxT(Integer.parseInt(parameter.getParameterName()));
                            break;

                        case "CI":
                            myWeatherArr[counter].setFeel(parameter.getParameterName());
                            break;
                    }
                    counter++;
                }
                counter = 0;

            }

            for (MyWeather myWeather : myWeatherArr) {
                list.add(myWeather);
            }
        }

        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }




    @Override
    public ResponseEntity lineNotify(List<MyWeather> list, Pm25 pm25) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Authorization", "Bearer wY0mnBNBL1xRLyaJQ2nfQvELJB81XHkLXrMMWQRhy6D"); //home
        requestHeaders.set("ContentType", "application/x-www-form-urlencoded");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        String msg = "";
        String stickerPackageId;
        String stickerId;
        Resource resource = null;



        for(MyWeather myWeather : list){


            if("臺中市".equals(myWeather.getLocationName())){




                msg += myWeather.getLocationName() +"\n";



                msg +=  "\n時間："   + myWeather.getStartTime().substring(5,16) + "~ " + myWeather.getEndTime().substring(5,16) +
                        "\n降雨率：" + myWeather.getRain() +"%"+
                        "\n體感："   + myWeather.getFeel() +
                        "\n天氣："   + myWeather.getSky()  +
                        "\n最高溫：" + myWeather.getMaxT() + "°C" +
                        "\n最低溫：" + myWeather.getMinT() + "°C\n";



                msg += "\n";


                //下雨 446 1994
                //好天氣 446  1988
                //下雨 789 10893


                if(myWeather.getRain() > 30){
                    stickerPackageId = 789 +"";
                    stickerId = 10893 +"";
                }else{
                    stickerPackageId = 446 +"";
                    stickerId = 1988 +"";
                }


//        String path = System.getProperty("user.dir") + "/src/main/resources/image/weather/" + skyNumber + ".PNG";
//        Resource resource = new FileSystemResource(new File(path));


                resource = new ClassPathResource("image\\weather\\"  + myWeather.getSkyImgNumber() + ".PNG");

                break;
            }

        }




        List<com.weathernotify.model.Pm25.Records> list2 = pm25.getRecords();
        int counter  = 0;
        msg += "--------------------------------\n" ;

        for(Records records  : list2){


            if("臺中市".equals(records.getCounty())){


                if(counter == 0 ) msg += "時間：" + records.getDataCreationDate() + "\n";

                msg += "地點：" + records.getSite() + "\n";
                msg += "懸浮微粒：" + records.getPm25() +  " " + records.getItemUnit() +  "\n\n";
                counter ++ ;
            }
        }




        body.add("message", msg);
        body.add("imageFile", resource);
        //body.add("stickerPackageId", stickerPackageId);
        //body.add("stickerId", stickerId);



        HttpEntity requestEntity = new HttpEntity(body, requestHeaders);

            restTemplate.exchange(
                "https://notify-api.line.me/api/notify",
                HttpMethod.POST,
                requestEntity,
                Object.class
        );

        return  ResponseEntity.ok("notify success");
    }
         /*
        1
        晴天

        2
        晴時多雲

        3
        多雲時晴

        4
        多雲

        5
        多雲時陰

        6
        陰時多雲

        7
        陰天

        8
        多雲短暫雨

        9
        多雲時陰短暫雨

        10
        陰時多雲短暫雨

        11
        陰短暫雨
             */


}
