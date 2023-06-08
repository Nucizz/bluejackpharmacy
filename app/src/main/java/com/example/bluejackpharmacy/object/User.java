package com.example.bluejackpharmacy.object;

import android.content.Context;
import android.widget.Toast;

import java.util.Base64;
import java.util.regex.Pattern;

public class User {

    private int id;
    private String name;
    private String email;
    private String phone;
    private boolean isVerified;

    public User(int id, String name, String email, String phone, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isVerified = isVerified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String isVerifiedString() {
        return isVerified ? "TRUE" : "FALSE";
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public static class Handler {

        public static String hashPassword(String text) {
            String encoded = Base64.getEncoder().encodeToString(text.getBytes());
            int len = encoded.length();

            if(len > 20) {
                StringBuilder limitEncoded = new StringBuilder();
                int index = 0;
                limitEncoded.append(encoded.substring(0, 20));
                while (index + 20 < len) {
                    char newCh = (char) (encoded.charAt(index + 20) + limitEncoded.charAt(index % 20));
                    limitEncoded.setCharAt(index % 20, newCh > 95 ? (char) ((newCh % 95) + 32) : newCh);
                    index++;
                }

                return limitEncoded.toString();
            } else {
                return encoded;
            }
        }

        public static  boolean validateName(Context ctx, String text) {
            if(text.isEmpty()) {
                Toast.makeText(ctx, "Name must not be empty!", Toast.LENGTH_SHORT).show();
                return false;
            }

            int len = text.length();
            if(len < 5) {
                Toast.makeText(ctx, "Name must contain at least 5 characters!", Toast.LENGTH_SHORT).show();
                return false;
            } else if(len > 50) {
                Toast.makeText(ctx, "Name must be shorter than 50 characters!", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }

        public static boolean validateEmail(Context ctx, String text) {
            if(text.isEmpty()) {
                Toast.makeText(ctx, "Email must not be empty!", Toast.LENGTH_SHORT).show();
                return false;
            }

            String newEmail = text.toLowerCase();
            if(!newEmail.endsWith(".com") && !newEmail.contains("@")){
                Toast.makeText(ctx, "Invalid email address!", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(text.length() > 50) {
                Toast.makeText(ctx, "Email must be shorter than 50 characters!", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }

        public static boolean validatePassword(Context ctx, String text) {
            if(text.isEmpty()) {
                Toast.makeText(ctx, "Password must not be empty!", Toast.LENGTH_SHORT).show();
                return false;
            }

            int len = text.length();
            if(len < 5) {
                Toast.makeText(ctx, "Password must contain at least 5 characters!", Toast.LENGTH_SHORT).show();
                return false;
            } else if(len > 20) {
                Toast.makeText(ctx, "Password must be shorter than 20 characters!", Toast.LENGTH_SHORT).show();
                return false;
            }

            boolean cAlpha = false, alpha = false, numeric = false;
            for(int i=0;i<len;i++) {
                if(text.charAt(i) >= '0' && text.charAt(i) <= '9'){
                    numeric = true;
                }
                if(text.charAt(i) >= 'a' && text.charAt(i) <= 'z'){
                    alpha = true;
                }
                if(text.charAt(i) >= 'A' && text.charAt(i) <= 'Z'){
                    cAlpha = true;
                }
            }

            if(!cAlpha || !alpha || !numeric) {
                Toast.makeText(ctx, "Invalid phone number format!", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }

        public static boolean validatePhone(Context ctx, String text) {
            if(text.isEmpty()) {
                Toast.makeText(ctx, "Phone must not be empty!", Toast.LENGTH_SHORT).show();
                return false;
            }

            int len = text.length(), i = 0;
            if(text.charAt(0) == '+') {
                i++;
            }

            for(;i<len;i++) {
                if(text.charAt(i) == ' ' || text.charAt(i) == '-') {
                    continue;
                } else if(text.charAt(i) < '0' || text.charAt(i) > '9'){
                    Toast.makeText(ctx, "Invalid phone number format!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            if(text.length() > 20) {
                Toast.makeText(ctx, "Phone must be shorter than 20 characters!", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }

        public static boolean textToBool(String state) {
            return state.equals("TRUE");
        }

        public static String boolToText(boolean state) {
            return state ? "TRUE" : "FALSE";
        }

    }

    public static class Current {
        private static User authenticated;

        public static User getInfo() {
            return authenticated;
        }

        public static void login(User user) {
            authenticated = user;
        }

        public static void logout() {
            authenticated = null;
        }
    }

}
