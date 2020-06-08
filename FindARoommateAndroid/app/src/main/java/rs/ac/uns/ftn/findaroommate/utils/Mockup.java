package rs.ac.uns.ftn.findaroommate.utils;

import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.dto.ReviewDto;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;
import rs.ac.uns.ftn.findaroommate.model.AdItem;
import rs.ac.uns.ftn.findaroommate.model.CharacteristicType;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;

public class Mockup {

    private static Mockup instance;
    private static ArrayList<Language> availableLanguages;
    private static ArrayList<Language> userLanguages;

    private static ArrayList<UserCharacteristic> availablePersonalities;
    private static ArrayList<UserCharacteristic> availableLifestyles;
    private static ArrayList<UserCharacteristic> availableSports;
    private static ArrayList<UserCharacteristic> availableMusics;
    private static ArrayList<UserCharacteristic> availableFilms;

    private static ArrayList<StayDto> staysHistory;
    private static ArrayList<ReviewDto> reviews;

    private static ArrayList<AdItem> adItems;

    private Mockup(){
        availableLanguages = initLanguages();

        userLanguages = initUserLanguages();

        availablePersonalities = initPersonalities();
        availableLifestyles = initLifestyle();
        availableMusics = initMusic();
        availableFilms = initFilm();
        availableSports = initSport();

        staysHistory = initStaysHistory();
        reviews = initReviews();

        adItems = initAdItems();

    }

    public static Mockup getInstance(){
        if (instance == null){
            instance = new Mockup();
        }
        return instance;
    }

    public  List<Language> getLanguagesDataSource(){
        return availableLanguages;
    }

    public List<Language> getUserLanguages(){
        return userLanguages;
    }

    public ArrayList<UserCharacteristic> getAvailablePersonalities() {
        return availablePersonalities;
    }

    public ArrayList<AdItem> getAdItems() {
        return adItems;
    }


    public  ArrayList<UserCharacteristic> getAvailableLifestyles() {
        return availableLifestyles;
    }

    public  ArrayList<UserCharacteristic> getAvailableSports() {
        return availableSports;
    }

    public  ArrayList<UserCharacteristic> getAvailableMusics() {
        return availableMusics;
    }

    public  ArrayList<UserCharacteristic> getAvailableFilms() {
        return availableFilms;
    }

    public ArrayList<StayDto> getStaysHistory() {
        return staysHistory;
    }

    public ArrayList<ReviewDto> getReviews() {
        return reviews;
    }

    private static ArrayList<Language> initLanguages(){
        Language lang1 = new Language("srp", "Serbian");
        Language lang2 = new Language("eng", "English");
        Language lang3 = new Language("spa", "Spanish");
        Language lang4 = new Language("deu", "German");
        Language lang5 = new Language("fra", "French");
        Language lang6 = new Language("por", "Portuguese");
        Language lang7 = new Language("rus", "Russian");
        Language lang8 = new Language("zho", "Chinise");
        Language lang9 = new Language("hun", "Hungarian");
        Language lang10 = new Language("ell", "Greek");
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(lang1);
        languages.add(lang2);
        languages.add(lang3);
        languages.add(lang4);
        languages.add(lang5);
        languages.add(lang6);
        languages.add(lang7);
        languages.add(lang8);
        languages.add(lang9);
        languages.add(lang10);

        return languages;
    }

    private static ArrayList<Language> initUserLanguages(){
        Language lang1 = new Language("srp", "Serbian");
        Language lang2 = new Language("eng", "English");
        ArrayList<Language> userLang = new ArrayList<>();
        userLang.add(lang1);
        userLang.add(lang2);
        return userLang;
    }

    private static ArrayList<UserCharacteristic> initPersonalities(){
        ArrayList<UserCharacteristic> personalities = new ArrayList<UserCharacteristic>();

        CharacteristicType type = CharacteristicType.PERSONALITY;
        UserCharacteristic c1 = new UserCharacteristic(type, "Calm");
        UserCharacteristic c2 = new UserCharacteristic(type, "Active");
        UserCharacteristic c3 = new UserCharacteristic(type, "Cheerful");
        UserCharacteristic c4 = new UserCharacteristic(type, "Friendly");
        UserCharacteristic c5 = new UserCharacteristic(type, "Energetic");
        UserCharacteristic c6 = new UserCharacteristic(type, "Organised");
        UserCharacteristic c7 = new UserCharacteristic(type, "Funny");
        UserCharacteristic c8 = new UserCharacteristic(type, "Tolerant");
        UserCharacteristic c9 = new UserCharacteristic(type, "Easygoing");
        UserCharacteristic c10 = new UserCharacteristic(type, "Sociable");

        personalities.add(c1);
        personalities.add(c2);
        personalities.add(c3);
        personalities.add(c4);
        personalities.add(c5);
        personalities.add(c6);
        personalities.add(c7);
        personalities.add(c8);
        personalities.add(c9);
        personalities.add(c10);

        return personalities;
    }

    private static ArrayList<UserCharacteristic> initLifestyle(){
        ArrayList<UserCharacteristic> personalities = new ArrayList<UserCharacteristic>();

        CharacteristicType type = CharacteristicType.LIFESTYLE;
        UserCharacteristic c1 = new UserCharacteristic(type, "Traveler");
        UserCharacteristic c2 = new UserCharacteristic(type, "Athlete");
        UserCharacteristic c3 = new UserCharacteristic(type, "Gamer");
        UserCharacteristic c4 = new UserCharacteristic(type, "Vegan");
        UserCharacteristic c5 = new UserCharacteristic(type, "Dancer");
        UserCharacteristic c6 = new UserCharacteristic(type, "Book lover");
        UserCharacteristic c7 = new UserCharacteristic(type, "Tech lover");
        UserCharacteristic c8 = new UserCharacteristic(type, "Walker");
        UserCharacteristic c9 = new UserCharacteristic(type, "Partier");
        UserCharacteristic c10 = new UserCharacteristic(type, "Workaholic");

        personalities.add(c1);
        personalities.add(c2);
        personalities.add(c3);
        personalities.add(c4);
        personalities.add(c5);
        personalities.add(c6);
        personalities.add(c7);
        personalities.add(c8);
        personalities.add(c9);
        personalities.add(c10);

        return personalities;
    }

    private static ArrayList<UserCharacteristic> initMusic(){
        ArrayList<UserCharacteristic> personalities = new ArrayList<UserCharacteristic>();

        CharacteristicType type = CharacteristicType.MUSIC;
        UserCharacteristic c1 = new UserCharacteristic(type, "Pop");
        UserCharacteristic c2 = new UserCharacteristic(type, "Rock");
        UserCharacteristic c3 = new UserCharacteristic(type, "Alternative");
        UserCharacteristic c4 = new UserCharacteristic(type, "Dance");
        UserCharacteristic c5 = new UserCharacteristic(type, "Hip-hop");
        UserCharacteristic c6 = new UserCharacteristic(type, "Jaaz");
        UserCharacteristic c7 = new UserCharacteristic(type, "Blues");
        UserCharacteristic c8 = new UserCharacteristic(type, "Tolerant");
        UserCharacteristic c9 = new UserCharacteristic(type, "Punk");
        UserCharacteristic c10 = new UserCharacteristic(type, "Metal");

        personalities.add(c1);
        personalities.add(c2);
        personalities.add(c3);
        personalities.add(c4);
        personalities.add(c5);
        personalities.add(c6);
        personalities.add(c7);
        personalities.add(c8);
        personalities.add(c9);
        personalities.add(c10);

        return personalities;
    }

    private static ArrayList<UserCharacteristic> initFilm(){
        ArrayList<UserCharacteristic> personalities = new ArrayList<UserCharacteristic>();

        CharacteristicType type = CharacteristicType.FILM;
        UserCharacteristic c1 = new UserCharacteristic(type, "Action");
        UserCharacteristic c2 = new UserCharacteristic(type, "Adventure");
        UserCharacteristic c3 = new UserCharacteristic(type, "Crime");
        UserCharacteristic c4 = new UserCharacteristic(type, "Horror");
        UserCharacteristic c5 = new UserCharacteristic(type, "Romance");
        UserCharacteristic c6 = new UserCharacteristic(type, "Thriller");
        UserCharacteristic c7 = new UserCharacteristic(type, "Sci-fi");
        UserCharacteristic c8 = new UserCharacteristic(type, "Animation");
        UserCharacteristic c9 = new UserCharacteristic(type, "Documentary");
        UserCharacteristic c10 = new UserCharacteristic(type, "Drama");

        personalities.add(c1);
        personalities.add(c2);
        personalities.add(c3);
        personalities.add(c4);
        personalities.add(c5);
        personalities.add(c6);
        personalities.add(c7);
        personalities.add(c8);
        personalities.add(c9);
        personalities.add(c10);

        return personalities;
    }

    private static ArrayList<UserCharacteristic> initSport(){
        ArrayList<UserCharacteristic> personalities = new ArrayList<UserCharacteristic>();

        CharacteristicType type = CharacteristicType.SPORT;
        UserCharacteristic c1 = new UserCharacteristic(type, "Football");
        UserCharacteristic c2 = new UserCharacteristic(type, "Basketball");
        UserCharacteristic c3 = new UserCharacteristic(type, "Tennis");
        UserCharacteristic c4 = new UserCharacteristic(type, "MMA");
        UserCharacteristic c5 = new UserCharacteristic(type, "Gym");
        UserCharacteristic c6 = new UserCharacteristic(type, "Golf");
        UserCharacteristic c7 = new UserCharacteristic(type, "Swimming");
        UserCharacteristic c8 = new UserCharacteristic(type, "Skateboarding");
        UserCharacteristic c9 = new UserCharacteristic(type, "Baseball");
        UserCharacteristic c10 = new UserCharacteristic(type, "Volleyball");

        personalities.add(c1);
        personalities.add(c2);
        personalities.add(c3);
        personalities.add(c4);
        personalities.add(c5);
        personalities.add(c6);
        personalities.add(c7);
        personalities.add(c8);
        personalities.add(c9);
        personalities.add(c10);

        return personalities;
    }

    private static ArrayList<StayDto> initStaysHistory(){
        ArrayList<StayDto> history = new ArrayList<>();

        /*StayDto s1 = new StayDto("Title 1", "Barcelona", new Date(), new Date(), AdStatus.IDLE, new User());
        StayDto s2 = new StayDto("Title 2 ", "Madrid", new Date(), new Date(), AdStatus.IDLE, new User());
        StayDto s3 = new StayDto("Title 3", "Moscow", new Date(), new Date(), AdStatus.IDLE, new User());
        StayDto s4 = new StayDto("Title 4", "Vienna", new Date(), new Date(), AdStatus.IDLE, new User());
        StayDto s5 = new StayDto("Title 5", "Istanbul", new Date(), new Date(), AdStatus.IDLE, new User());

        history.add(s1);
        history.add(s2);
        history.add(s3);
        history.add(s4);
        history.add(s5);*/

        return history;
    }

    private static ArrayList<ReviewDto> initReviews(){
        ArrayList<ReviewDto> reviewDtos = new ArrayList<>();

        ReviewDto r1 = new ReviewDto("Paloma", "5", "Superb", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        ReviewDto r2 = new ReviewDto("Margareth", "4", "Great time", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        ReviewDto r3 = new ReviewDto("Juana", "5", "Best", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        ReviewDto r4 = new ReviewDto("Jon", "4", "Friend for life", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        ReviewDto r5 = new ReviewDto("Milos", "4", "Fine", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

        reviewDtos.add(r1);
        reviewDtos.add(r2);
        reviewDtos.add(r3);
        reviewDtos.add(r4);
        reviewDtos.add(r5);


        return reviewDtos;
    }

    private static ArrayList<AdItem> initAdItems(){
        ArrayList<AdItem> adItems = new ArrayList<>();

        AdItem a1 = AdItem.builder().name("Wifi").build();
        AdItem a2 = AdItem.builder().name("Lift").build();
        AdItem a3 = AdItem.builder().name("Washing machine").build();
        AdItem a4 = AdItem.builder().name("Dishing").build();
        AdItem a5 = AdItem.builder().name("Room service").build();
        AdItem a6 = AdItem.builder().name("Doorman").build();
        AdItem a7 = AdItem.builder().name("Tv").build();
        AdItem a8 = AdItem.builder().name("Heating").build();
        AdItem a9 = AdItem.builder().name("Air cond").build();
        AdItem a10 = AdItem.builder().name("Furnished").build();
        AdItem a11 = AdItem.builder().name("Parking").build();
        AdItem a12 = AdItem.builder().name("Garage").build();
        AdItem a13 = AdItem.builder().name("Pool").build();
        AdItem a14 = AdItem.builder().name("Terrace").build();
        AdItem a15 = AdItem.builder().name("Pet friendly").build();
        AdItem a16 = AdItem.builder().name("Garden").build();
        AdItem a17 = AdItem.builder().name("Balcony").build();
        AdItem a18 = AdItem.builder().name("Dryer").build();
        AdItem a19 = AdItem.builder().name("Private lift").build();
        AdItem a20 = AdItem.builder().name("Natural ligth").build();

        adItems.add(a1);
        adItems.add(a2);
        adItems.add(a3);
        adItems.add(a4);
        adItems.add(a5);
        adItems.add(a6);
        adItems.add(a7);
        adItems.add(a8);
        adItems.add(a9);
        adItems.add(a10);
        adItems.add(a11);
        adItems.add(a12);
        adItems.add(a13);
        adItems.add(a14);
        adItems.add(a15);
        adItems.add(a16);
        adItems.add(a17);
        adItems.add(a18);
        adItems.add(a19);
        adItems.add(a20);

        return adItems;
    }
}
