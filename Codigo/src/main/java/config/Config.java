package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config{
    private Properties properties;
    public Config(String filename){
        properties = new Properties();

        try(InputStream input = getClass().getClassLoader().getResourceAsStream(filename)){
            if(input == null){
                throw new IOException("Arquivo de configuração '" + filename + "' não encontrado.");
            }
            properties.load(input);
        }catch(IOException ex){
            ex.printStackTrace();          
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

}