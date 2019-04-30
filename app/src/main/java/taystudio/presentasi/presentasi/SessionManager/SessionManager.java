package taystudio.presentasi.presentasi.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static final String PREF_NAME = "session_laporkeun";
    private static final int PRIVATE_MODE = 0;

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "your_example@mail.com";
    private static final String KEY_NAME  = "Name of User";
    private static final String KEY_PHONE  = "Example";

    public  SessionManager(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setKeyEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public  String getKeyEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public void setKeyName(String name)
    {
        editor.putString(KEY_NAME,name);
        editor.commit();
    }

    public String getKeyName() {
        return sharedPreferences.getString(KEY_NAME,"Your Name");
    }

    public void setKeyId(String id)
    {
        editor.putString(KEY_ID,id);
        editor.commit();
    }

    public String getKeyId()
    {
        return sharedPreferences.getString(KEY_ID,"");
    }

    public void setKeyPhone(String phone)
    {
        editor.putString(KEY_PHONE,phone);
        editor.commit();
    }

    public String getKeyPhone()
    {
        return sharedPreferences.getString(KEY_PHONE,"");
    }

}
