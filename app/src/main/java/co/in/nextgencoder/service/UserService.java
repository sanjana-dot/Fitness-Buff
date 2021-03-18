package co.in.nextgencoder.service;

import androidx.annotation.NonNull;

import co.in.nextgencoder.model.User;
import co.in.nextgencoder.util.CallBack;

public interface UserService {
    public void updateUser(CallBack<Boolean> finishedCallBack, User user);
    public void getSurvey(CallBack<Boolean> finishedCallBack, String id, Double height , Double weight);

    public void getUserById(@NonNull CallBack<User> finishedCallback, String id);
}
