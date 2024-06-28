package me.sanghyuk.subwayboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubwayService {

    public List<String> subwayApi(String apiName, String[] addParam) throws Exception {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" +  URLEncoder.encode("6657744c4468616e3737436a6c7754","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" +  URLEncoder.encode("xml","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
            urlBuilder.append("/" + URLEncoder.encode(apiName,"UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
            urlBuilder.append("/" + URLEncoder.encode("100","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
            // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

            // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
            for(int i=0; i<addParam.length; i++){
                    urlBuilder.append("/" + URLEncoder.encode(addParam[i],"UTF-8")); /* 서비스별 추가 요청인자들*/
            }

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");
            BufferedReader rd;

            // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            List<String> result = new ArrayList<>();
            String line;

            while ((line = rd.readLine()) != null) {
                    result.add(line);
            }
            rd.close();
            conn.disconnect();
            return result;
    }
    public List<String> getStnName(String stnNm) throws Exception {

            String[] add = {" ", stnNm, " "};
            List<String> list = subwayApi("SearchSTNBySubwayLineInfo", add);
            List<String> result = new ArrayList<>();
            String nm = "";
            String no = "";
            String cd = "";
            for(int i=0; i<list.size(); i++){
                    if(list.get(i).startsWith("<STATION_NM>")){
                            nm = cutString(list.get(i));
                    }else if(list.get(i).startsWith("<LINE_NUM>")){
                            no = cutString(list.get(i));
                    }
                    if(nm != "" && no != ""){
                            result.add(no+"."+nm);
                            nm="";
                            no="";
                    }
            }
            return result;
    }

    public String cutString(String str){
            String[] split = str.split(">");
            String result = split[1].split("<")[0];
            return result;
    }
}
