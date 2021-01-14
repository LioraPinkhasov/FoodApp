package com.example.foodapp.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAysM3Q9Q:APA91bHY-aY1Tzj4x0Un32UwP8bO3qCfuJnQz29ZMGOYQCDmQl0nti80b6A-SbzR9uhLFF2-jtku9Pl4UJUXuc3dbPBv-WNklYiK_T6fXSk7EB1gVO7EjyFQyQgPyDDdm7CotGyFYhNh" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
