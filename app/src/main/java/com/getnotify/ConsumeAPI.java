package com.getnotify;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ConsumeAPI {

    private static final String Key = "AIzaSyAkL7MteqiUjMsqjdLIkMWEMEKlmmzXpfQ";
    private static final String url = "https://notifyapi.herokuapp.com/";
    public static IRetrofit objIRetrofit = null;

    public static IRetrofit postLog() {
        if (objIRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            objIRetrofit = retrofit.create(IRetrofit.class);
        }
        return objIRetrofit;
    }


    public interface IRetrofit {

        //        public Call<LogDetailResponse> postLogDetails(@Field("UserId") String UserId,
//                                                      @Field("MACId") String MACId,
//                                                      @Field("Message") String Message,
//                                                      @Field("CallLog") String CallLog,
//                                                      @Field("IsCall") Boolean IsCall,
//                                                      @Field("IsSMS") Boolean IsSMS);
//        @Headers({"Content-Type:application/json"})
//        @POST("/api/postLogDetails")
//        public Call<LogDetailResponse> postLogDetails(@Body UserDetails ObjUserDetails);

        @Headers({"Content-Type:application/json"})
        @POST("/api/postAESLogDetails")
        public Call<LogDetailResponse> postAESLogDetails(@Body UserDetails ObjUserDetails);

        @Headers({"Content-Type:application/json"})
        @POST("/api/getencrptoken")
        public Call<GetEncrptokenRes> GetAESToken(@Body UserAESTokenReq ObjUserAESTokenReq);
    }
}


//public class ConsumeAPI extends AsyncTask<Void,Void,Void> {
//    UserDetails UserDe;
//
//
//    public ConsumeAPI(UserDetails ObjUserD){
//        this.UserDe = ObjUserD;
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//
//        try {
////            URL url = new URL("https://notifyapi.herokuapp.com/postLogDetails"); //Enter URL here
//////            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//////            httpURLConnection.setDoOutput(true);
//////            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
//////            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
//////            httpURLConnection.connect();
//////            JSONObject jsonObject = new JSONObject();
//////            jsonObject.put("Username", UserDe.getUserId());
//////            jsonObject.put("MacId", UserDe.getMACId());
//////            jsonObject.put("Message", UserDe.getMessage());
//////            jsonObject.put("Date",  DateFormat.getDateTimeInstance().format(new Date()));
//////            jsonObject.put("CallLog", UserDe.getCallLog());
//////            jsonObject.put("IsCall",UserDe.getCallLog());
//////            jsonObject.put("IsSMS",UserDe.getSMS());
//////            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//////            wr.writeBytes(jsonObject.toString());
//////            wr.flush();
//////            wr.close();
//
//
//            URL url = new URL("https://notifyapi.herokuapp.com/postLogDetails");
//            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//
//            Map<String,Object> jsonObject = new HashMap<String,Object>();
//            jsonObject.put("Username", UserDe.getUserId());
//            jsonObject.put("MacId", UserDe.getMACId());
//            jsonObject.put("Message", UserDe.getMessage());
//            jsonObject.put("Date",  DateFormat.getDateTimeInstance().format(new Date()));
//            jsonObject.put("CallLog", UserDe.getCallLog());
//            jsonObject.put("IsCall",UserDe.getCall());
//            jsonObject.put("IsSMS",UserDe.getSMS());
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getQuery(jsonObject));
//            writer.flush();
//            writer.close();
//            os.close();
//            conn.connect();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        catch (JSONException e) {
////            e.printStackTrace();
////        }
//        return null;
//    }
//
//    private String getQuery(Map<String,Object> params) throws UnsupportedEncodingException
//    {
//        StringBuilder result = new StringBuilder();
////        boolean first = true;
////        for (String pair : params)
////        {
////            if (first)
////                first = false;
////            else
////                result.append("&");
////
////            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
////            result.append("=");
////            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
////        }
//        StringBuilder sb = new StringBuilder();
//        for(HashMap.Entry<String,Object> e : params.entrySet()){
//            if(sb.length() > 0){
//                sb.append('&');
//            }
//            sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode(String.valueOf(e.getValue()), "UTF-8"));
//        }
//
//        return sb.toString();
//    }
//}
