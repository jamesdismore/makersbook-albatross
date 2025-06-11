package com.makersacademy.acebook.util;

import com.makersacademy.acebook.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AvatarAssistant {

    public static void overwriteAvatar(MultipartFile newAvatarImage, User user) {

        try {

            URL url = Paths.get("target", "classes/static/images/userAvatars").toUri().toURL();

            String filename = String.valueOf(user.getId()) ; // save as {userId}.jpg
            Path path = Paths.get(url.getPath() + "/" + filename + ".jpg" );
            Files.write(path, newAvatarImage.getBytes());
            user.setAvatar(filename);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
