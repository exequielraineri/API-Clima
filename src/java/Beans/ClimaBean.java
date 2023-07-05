/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ADMIN
 */
@Stateless
@Named
public class ClimaBean {

    private String ciudad;
    StringBuilder stringBuilder;
    private String city = "";
    private int temperatura;

    double lon = 0.0;
    double lat = 0.0;
    int weatherId = 0;
    String weatherMain = "";
    String weatherDescription = "";
    String weatherIcon = "";
    int temperature = 0;
    int temp_min = 0;
    int temp_max = 0;
    int pressure = 0;
    int humidity = 0;
    double windSpeed = 0.0;
    int windDeg = 0;
    int visibility = 0;
    int dt = 0;
    String country = "";
    long sunrise = 0;
    long sunset = 0;
    String cityName = "";
    int cod = 0;

    String mensaje = "";

    public String mostrar() {

        try {
            String ciudadABuscar = ciudad.replace(" ", "%20");
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + ciudadABuscar + "&lang=es&appid=dcf75e85aedffd5f9b1be435c1455fba&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            Scanner scanner;
            stringBuilder = new StringBuilder();
            if (responseCode != 200) {
                throw new RuntimeException("Ocurrio un error!" + responseCode);
            } else {

                scanner = new Scanner(url.openStream(),"UTF-8");

                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
            }
            System.out.println(stringBuilder);

            System.out.println("\n\n");

            // Analizar la cadena JSON
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            // Obtener los valores de los campos específicos
            JSONObject coordObject = jsonObject.getJSONObject("coord");
            lon = coordObject.getDouble("lon");
            lat = coordObject.getDouble("lat");

            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            weatherId = weatherObject.getInt("id");
            weatherMain = weatherObject.getString("main");
            weatherDescription = weatherObject.getString("description");
            weatherIcon = weatherObject.getString("icon");

            JSONObject mainObject = jsonObject.getJSONObject("main");
            temperature = mainObject.getInt("temp");
            temp_min = mainObject.getInt("temp_min");
            temp_max = mainObject.getInt("temp_max");
            pressure = mainObject.getInt("pressure");
            humidity = mainObject.getInt("humidity");

            JSONObject windObject = jsonObject.getJSONObject("wind");
            windSpeed = windObject.getDouble("speed");
            windDeg = windObject.getInt("deg");

            visibility = jsonObject.getInt("visibility");

            dt = jsonObject.getInt("dt");

            JSONObject sysObject = jsonObject.getJSONObject("sys");
            country = sysObject.getString("country");
            sunrise = sysObject.getLong("sunrise");
            sunset = sysObject.getLong("sunset");

            cityName = jsonObject.getString("name");
            cod = jsonObject.getInt("cod");

            // Imprimir los valores obtenidos
            System.out.println("Coordenadas: lat=" + lat + ", lon=" + lon);
            System.out.println("Weather: id=" + weatherId + ", main=" + weatherMain + ", description=" + weatherDescription);
            System.out.println("Temperatura: " + temperature);
            System.out.println("Presión: " + pressure);
            System.out.println("Humedad: " + humidity);
            System.out.println("Velocidad del viento: " + windSpeed);
            System.out.println("Dirección del viento: " + windDeg);
            System.out.println("Visibilidad: " + visibility);
            System.out.println("Fecha y hora: " + dt);
            System.out.println("País: " + country);
            System.out.println("Amanecer: " + sunrise);
            System.out.println("Atardecer: " + sunset);
            System.out.println("Ciudad: " + cityName);
            System.out.println("Código: " + cod);
            ciudadABuscar = "";
        } catch (ProtocolException ex) {
            Logger.getLogger(ClimaBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClimaBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return "index";

        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public void obtenerCiudad() {
        JSONObject jSONObject = new JSONObject(stringBuilder.toString());
        String temp = jSONObject.getString("name");

        System.out.println("Aquiiiiiiiiiiiiii-->");
        System.out.println("ciudad: " + temp);

        city = temp;

    }

    public void obtenerTemp() {
        JSONObject jSONObject = new JSONObject(stringBuilder.toString());

        JSONObject temp = jSONObject.getJSONObject("main");
        System.out.println("temperatura en json: " + temp.get("temp"));

        float tempJSON = temp.getFloat("temp");
        int tempActual = (int) Math.ceil(tempJSON);

        temperatura = (int) Math.ceil(tempActual);
    }

    public String mos() {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=tucuman&lang=sp&appid=dcf75e85aedffd5f9b1be435c1455fba&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            StringBuilder stringBuilder = new StringBuilder();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Ocurrio un error!" + responseCode);
            } else {

                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
            }

            System.out.println(stringBuilder.toString());
            //JSONArray jSONArray=new JSONArray(stringBuilder.toString());
            JSONObject jSONObject = new JSONObject(stringBuilder.toString());
            System.out.println("Cordenada: " + jSONObject.get("coord"));
            System.out.println("weather: " + jSONObject.get("weather"));
            System.out.println("Main: " + jSONObject.get("main"));

            JSONObject temp = jSONObject.getJSONObject("main");
            System.out.println("temperatura en json: " + temp.get("temp"));

            float tempJSON = temp.getFloat("temp");
            System.out.println("desde int: " + tempJSON);
            System.out.println("Resta: " + (tempJSON - 273.15f));
            int tempActual = (int) Math.ceil(tempJSON - 273.15f);
            System.out.println("Temperatura actual: " + tempActual + "°");

        } catch (MalformedURLException ex) {
            Logger.getLogger(ClimaBean.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(ClimaBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        DecimalFormat df=new DecimalFormat("#.##");
        return df.format(windSpeed*3.6);
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(int windDeg) {
        this.windDeg = windDeg;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

}
