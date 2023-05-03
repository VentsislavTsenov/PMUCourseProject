package com.example.pmucourseproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pmucourseproject.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private Button zoomin_button;
    private Button zoomout_button;
    private Button back_button;
    private Button getCurrLocation;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private List<List<Marker>> markers;

    private enum Oblasti {
        Vidin,
        Montana,
        Vratsa,
        Pleven,
        VTurnovo,
        Ruse,
        Silistra,
        Dobritch,
        SofiaOblast,
        SofiaGrad,
        Lovech,
        Gabrovo,
        Turgovishte,
        Razgrad,
        Shumen,
        Varna,
        Pernik,
        Kiustendil,
        Pazardzhik,
        Plovdiv,
        StZagora,
        Sliven,
        Qmbol,
        Burgas,
        Blagoevgrad,
        Smolqn,
        Kurdzhali,
        Haskovo
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        markers = new ArrayList<List<Marker>>();
        for (int i = 0; i < 28; i++) {
            markers.add(new ArrayList<Marker>());
        }
        /*
        ОБЛАСТИ:
        0 - Видин
        1 - Монтана
        2 - Враца
        3 - Плевен
        4 - В. Търново
        5 - Русе
        6 - Силистра
        7 - Добрич
        8 - Соф. област
        9 - София град
        10 - Ловеч
        11 - Габрово
        12 - Търговище
        13 - Разград
        14 - Шумен
        15 - Варна
        16 - Перник
        17 - Кюстендил
        18 - Пазарджик
        19 - Пловдив
        20 - Ст. Загора
        21 - Сливен
        22 - Ямбол
        23 - Бургас
        24 - Благоевград
        25 - Смолян
        26 - Кърджали
        27 - Хасково
        */

        zoomout_button = (Button) findViewById(R.id.zoomoutButton);
        zoomout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomBy(-1));
            }
        });

        zoomin_button = (Button) findViewById(R.id.zoominButton);
        zoomin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomBy(1));
            }
        });

        back_button = (Button) findViewById(R.id.backButton);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, MainActivity.class));
            }
        });

        getCurrLocation = (Button) findViewById(R.id.currLocationButton);
        getCurrLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getCurrentLocation(); }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        Marker belogradchishkiskali = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.62267376954258, 22.683860734380083))
                        .title("Белоградчишки скали")
                        .snippet("гр. Белоградчик, обл. Видин")
        );
        belogradchishkiskali.setTag("belogradchishkiskali");
        markers.get(Oblasti.Vidin.ordinal()).add(belogradchishkiskali);

        Marker peshteramagurata = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.727507691077655, 22.583899437278546))
                        .title("Пещера Магурата")
                        .snippet("с. Рабиша, обл. Видин")
        );
        peshteramagurata.setTag("peshteramagurata");
        markers.get(Oblasti.Vidin.ordinal()).add(peshteramagurata);

        Marker babavida = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.9930899567918, 22.886179452633723))
                        .title("Крепост Баба Вида")
                        .snippet("гр. Видин, обл. Видин")
        );
        babavida.setTag("babavida");
        markers.get(Oblasti.Vidin.ordinal()).add(babavida);

        Marker svetatroica = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.835569825308376, 23.48721521389186))
                        .title("Църква Света Троица")
                        .snippet("гр. Банско, обл. Благоевград")
        );
        svetatroica.setTag("svetatroica");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(svetatroica);

        Marker nikolavaptsarov = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.855775824851925, 23.503330734633835))
                        .title("Къща музей Никола Вапцаров")
                        .snippet("гр. Банско, обл. Благоевград")
        );
        nikolavaptsarov.setTag("nikolavaptsarov");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(nikolavaptsarov);

        Marker vruhvihren = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.76744524877845, 23.399146241173238))
                        .title("Връх Вихрен")
                        .snippet("пл. Пирин, обл. Благоевград")
        );
        vruhvihren.setTag("vruhvihren");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(vruhvihren);

        Marker neofitrilski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.836266183312354, 23.48689202937816))
                        .title("Къща музей Неофит Рилски")
                        .snippet("гр. Банско, обл. Благоевград")
        );
        neofitrilski.setTag("neofitrilski");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(neofitrilski);

        Marker tsurkvateodor = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.97016142347097, 23.477059683215337))
                        .title("Св. св. Теодор Тирон и Теодор Стратилат")
                        .snippet("с. Добърско, обл. Благоевград")
        );
        tsurkvateodor.setTag("tsurkvateodor");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(tsurkvateodor);

        Marker istoricheskimuzei = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.52341886385251, 23.39647563716875))
                        .title("Исторически музей Мелник")
                        .snippet("гр. Мелник, обл. Благоевград")
        );
        istoricheskimuzei.setTag("istoricheskimuzei");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(istoricheskimuzei);

        Marker rozhenskimanastir = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.5305989190997, 23.426642042712384))
                        .title("Манастир Рождество Богородично")
                        .snippet("с. Рожен, обл. Благоевград")
        );
        rozhenskimanastir.setTag("rozhenskimanastir");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(rozhenskimanastir);

        Marker rupitehram = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.45917232161283, 23.26435452736727))
                        .title("Храм Света Петка Българска")
                        .snippet("гр. Петрич, обл. Благоевград")
        );
        rupitehram.setTag("rupitehram");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(rupitehram);

        Marker samuilovakrepost = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.394800914967256, 23.02900905407199))
                        .title("Самуилова крепост")
                        .snippet("гр. Петрич, обл. Благоевград")
        );
        samuilovakrepost.setTag("samuilovakrepost");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(samuilovakrepost);

        Marker muzeinesebur = mMap.addMarker(
                new MarkerOptions()
                        .position((new LatLng(42.65868281299888, 27.731013340736315)))
                        .title("Музей Старинен Несебър")
                        .snippet("гр. Несебър, обл. Бургас")
        );
        muzeinesebur.setTag("muzeinesebur");
        markers.get(Oblasti.Burgas.ordinal()).add(muzeinesebur);

        Marker hrampametnikburgas = mMap.addMarker(
                new MarkerOptions()
                        .position((new LatLng(42.49624765012237, 27.47410276723598)))
                        .title("Храм Св. св. Кирил и Методий")
                        .snippet("гр. Бургас, обл. Бургас")
        );
        hrampametnikburgas.setTag("hrampametnikburgas");
        markers.get(Oblasti.Burgas.ordinal()).add(hrampametnikburgas);

        Marker muzeimalkoturnovo = mMap.addMarker(
                new MarkerOptions()
                        .position((new LatLng(41.97965265692785, 27.523778508355754)))
                        .title("Исторически музей Малко Търново")
                        .snippet("гр. Малко Търново, обл. Бургас")
        );
        muzeimalkoturnovo.setTag("muzeimalkoturnovo");
        markers.get(Oblasti.Burgas.ordinal()).add(muzeimalkoturnovo);

        Marker petrovaniva = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.061629595989054, 27.528368576005757))
                        .title("Местност Петрова нива")
                        .snippet("гр. Малко Търново, обл. Бургас")
        );
        petrovaniva.setTag("petrovaniva");
        markers.get(Oblasti.Burgas.ordinal()).add(petrovaniva);

        Marker voennomorskimuzei = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.200435310955356, 27.92155402329529))
                        .title("Военноморски музей")
                        .snippet("гр. Варна, обл. Варна")
        );
        voennomorskimuzei.setTag("voennomorskimuzei");
        markers.get(Oblasti.Varna.ordinal()).add(voennomorskimuzei);

        Marker muzeinamozaikite = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.225279011353244, 27.584913510265608))
                        .title("Музей на мозайките")
                        .snippet("гр. Девня, обл. Варна")
        );
        muzeinamozaikite.setTag("muzeinamozaikite");
        markers.get(Oblasti.Varna.ordinal()).add(muzeinamozaikite);

        Marker kreposttsarevets = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.08638091650004, 25.654485117700254))
                        .title("Крепост Царевец")
                        .snippet("гр. Велико Търново, обл. Велико Търново")
        );
        kreposttsarevets.setTag("kreposttsarevets");
        markers.get(Oblasti.VTurnovo.ordinal()).add(kreposttsarevets);

        Marker alekokonstantinov = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.62069494127827, 25.340017067956282))
                        .title("Къща музей Алеко Константинов")
                        .snippet("гр. Свищов, обл. Велико Търново")
        );
        alekokonstantinov.setTag("alekokonstantinov");
        markers.get(Oblasti.VTurnovo.ordinal()).add(alekokonstantinov);

        Marker nikopolis = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.23485496485085, 25.61514077246202))
                        .title("Древен град Никополис ад Иструм")
                        .snippet("с. Никюп, обл. Велико Търново")
        );
        nikopolis.setTag("nikopolis");
        markers.get(Oblasti.VTurnovo.ordinal()).add(nikopolis);

        Marker peshteraledenika = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.20462758110822, 23.490829242795385))
                        .title("Пещера Леденика")
                        .snippet("гр. Враца, обл. Враца")
        );
        peshteraledenika.setTag("peshteraledenika");
        markers.get(Oblasti.Vratsa.ordinal()).add(peshteraledenika);

        Marker kaleto = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.14013657226491, 23.704216454438246))
                        .title("Археологически комплекс Калето")
                        .snippet("гр. Мездра, обл. Враца")
        );
        kaleto.setTag("kaleto");
        markers.get(Oblasti.Vratsa.ordinal()).add(kaleto);

        Marker vruhokolchitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.155035101062374, 23.583819868177134))
                        .title("Връх Околчица, Пл. Вола")
                        .snippet("с. Челопек, обл. Враца")
        );
        vruhokolchitsa.setTag("vruhokolchitsa");
        markers.get(Oblasti.Vratsa.ordinal()).add(vruhokolchitsa);

        Marker radetski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.79990914628048, 23.677059593989675))
                        .title("Параход Радецки")
                        .snippet("гр. Козлодуй, обл. Враца")
        );
        radetski.setTag("radetski");
        markers.get(Oblasti.Vratsa.ordinal()).add(radetski);

        Marker muzeietur = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.803606016898634, 25.349183867915155))
                        .title("Музей на открито Етър")
                        .snippet("гр. Габрово, обл. Габрово")
        );
        muzeietur.setTag("muzeietur");
        markers.get(Oblasti.Gabrovo.ordinal()).add(muzeietur);

        Marker muzeigabrovo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.87050653304854, 25.319946515790974))
                        .title("Исторически музей Габрово")
                        .snippet("гр. Габрово, обл. Габрово")
        );
        muzeigabrovo.setTag("muzeigabrovo");
        markers.get(Oblasti.Gabrovo.ordinal()).add(muzeigabrovo);

        Marker bozhentsite = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.873252129370094, 25.424700976116785))
                        .title("Исторически резерват Боженци")
                        .snippet("с. Боженците, обл. Габрово")
        );
        bozhentsite.setTag("bozhentsite");
        markers.get(Oblasti.Gabrovo.ordinal()).add(bozhentsite);

        Marker desetmuzeq = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.86534099114193, 25.4867515255891))
                        .title("10 музейни обекта в Трявна")
                        .snippet("гр. Трявна, обл. Габрово")
        );
        desetmuzeq.setTag("desetmuzeq");
        markers.get(Oblasti.Gabrovo.ordinal()).add(desetmuzeq);

        Marker drqnovskimanastir = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.95078311685169, 25.43175941025172))
                        .title("Дряновски манастир")
                        .snippet("гр. Дряново, обл. Габрово")
        );
        drqnovskimanastir.setTag("drqnovskimanastir");
        markers.get(Oblasti.Gabrovo.ordinal()).add(drqnovskimanastir);

        Marker peshterabachokiro = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.94723801744185, 25.43028346792235))
                        .title("Пещера Бачо Киро")
                        .snippet("гр. Дряново, обл. Габрово")
        );
        peshterabachokiro.setTag("peshterabachokiro");
        markers.get(Oblasti.Gabrovo.ordinal()).add(peshterabachokiro);

        Marker kolioficheto = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.97964201035752, 25.477339383265495))
                        .title("Музей „Кольо Фичето“")
                        .snippet("гр. Дряново, обл. Габрово")
        );
        kolioficheto.setTag("kolioficheto");
        markers.get(Oblasti.Gabrovo.ordinal()).add(kolioficheto);

        Marker iordaniovkov = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.564963555817705, 27.832458025624216))
                        .title("Дом-паметник „Йордан Йовков“")
                        .snippet("гр. Добрич, обл. Добрич")
        );
        iordaniovkov.setTag("iordaniovkov");
        markers.get(Oblasti.Dobritch.ordinal()).add(iordaniovkov);

        Marker galeriqdobritch = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.56822004366037, 27.82658775445994))
                        .title("Художествена галерия Добрич")
                        .snippet("гр. Добрич, обл. Добрич")
        );
        galeriqdobritch.setTag("galeriqdobritch");
        markers.get(Oblasti.Dobritch.ordinal()).add(galeriqdobritch);

        Marker kompleksdvoretsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.40344549396348, 28.145763583286875))
                        .title("Балчишки дворец")
                        .snippet("гр. Балчик, обл. Добрич")
        );
        kompleksdvoretsa.setTag("kompleksdvoretsa");
        markers.get(Oblasti.Dobritch.ordinal()).add(kompleksdvoretsa);

        Marker botanicheskagradina = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.40447610776023, 28.14791092561611))
                        .title("Ботаническа градина Балчик")
                        .snippet("гр. Балчик, обл. Добрич")
        );
        botanicheskagradina.setTag("botanicheskagradina");
        markers.get(Oblasti.Dobritch.ordinal()).add(botanicheskagradina);

        Marker noskaliakra = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.36110377693415, 28.46571283910778))
                        .title("Нос Калиакра")
                        .snippet("гр. Каварна, обл. Добрич")
        );
        noskaliakra.setTag("noskaliakra");
        markers.get(Oblasti.Dobritch.ordinal()).add(noskaliakra);

        Marker muzeikavarna = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.43230812939961, 28.339735310276023))
                        .title("Исторически музей Каварна")
                        .snippet("гр. Каварна, обл. Добрич")
        );
        muzeikavarna.setTag("muzeikavarna");
        markers.get(Oblasti.Dobritch.ordinal()).add(muzeikavarna);

        Marker ioanpredtecha = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.62668082417106, 25.370259612033863))
                        .title("Манастир „Св. Йоан Предтеча“")
                        .snippet("гр. Кърджали, обл. Кърджали")
        );
        ioanpredtecha.setTag("ioanpredtecha");
        markers.get(Oblasti.Kurdzhali.ordinal()).add(ioanpredtecha);

        Marker perperikon = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.71504384064629, 25.46589681019045))
                        .title("Древен свещен град Перперикон")
                        .snippet("гр. Кърджали, обл. Кърджали")
        );
        perperikon.setTag("perperikon");
        markers.get(Oblasti.Kurdzhali.ordinal()).add(perperikon);

        Marker muzeikurdzhali = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.64717346259631, 25.369740000388894))
                        .title("Исторически музей Кърджали")
                        .snippet("гр. Кърджали, обл. Кърджали")
        );
        muzeikurdzhali.setTag("muzeikurdzhali");
        markers.get(Oblasti.Kurdzhali.ordinal()).add(muzeikurdzhali);

        Marker galeriqkiustendil = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.28285633430414, 22.689223340901712))
                        .title("Художествена галерия „Владимир Димитров Майстора“")
                        .snippet("гр. Кюстендил, обл. Кюстендил")
        );
        galeriqkiustendil.setTag("galeriqkiustendil");
        markers.get(Oblasti.Kiustendil.ordinal()).add(galeriqkiustendil);

        Marker dimitarpeshev = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.28341987087017, 22.689042327407762))
                        .title("Къща музей „Димитър Пешев“")
                        .snippet("гр. Кюстендил, обл. Кюстендил")
        );
        dimitarpeshev.setTag("dimitarpeshev");
        markers.get(Oblasti.Kiustendil.ordinal()).add(dimitarpeshev);

        Marker svgeorgi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.27089849596227, 22.677037569736406))
                        .title("Музей „Средновековна църква Св. Георги“")
                        .snippet("гр. Кюстендил, обл. Кюстендил")
        );
        svgeorgi.setTag("svgeorgi");
        markers.get(Oblasti.Kiustendil.ordinal()).add(svgeorgi);

        Marker muzeikiustendil = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.287490714634295, 22.685155398572594))
                        .title("Регионален исторически музей Кюстендил")
                        .snippet("гр. Кюстендил, обл. Кюстендил")
        );
        muzeikiustendil.setTag("muzeikiustendil");
        markers.get(Oblasti.Kiustendil.ordinal()).add(muzeikiustendil);

        Marker vruhruen = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.1584683678354, 22.516363780115377))
                        .title("Връх Руен")
                        .snippet("Осоговска планина, обл. Кюстендил")
        );
        vruhruen.setTag("vruhruen");
        markers.get(Oblasti.Kiustendil.ordinal()).add(vruhruen);

        Marker muzeiblagoevgrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.02052905828981, 23.10301122739487))
                        .title("Регионален исторически музей Благоевград")
                        .snippet("гр. Благоевград, обл. Благоевград")
        );
        muzeiblagoevgrad.setTag("muzeiblagoevgrad");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(muzeiblagoevgrad);

        Marker kompleksvarosha = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.02127236402137, 23.103450467876296))
                        .title("Възрожденски комплекс Вароша")
                        .snippet("гр. Благоевград, обл. Благоевград")
        );
        kompleksvarosha.setTag("kompleksvarosha");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(kompleksvarosha);

        Marker rilskimanastir = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.133469099391625, 23.341623136906044))
                        .title("Рилски манастир")
                        .snippet("общ. Рила, обл. Кюстендил")
        );
        rilskimanastir.setTag("rilskimanastir");
        markers.get(Oblasti.Kiustendil.ordinal()).add(rilskimanastir);

        Marker stobskipiramidi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.09330065617334, 23.122166928543283))
                        .title("Стобски пирамиди")
                        .snippet("с. Стоб, обл. Кюстендил")
        );
        stobskipiramidi.setTag("stobskipiramidi");
        markers.get(Oblasti.Kiustendil.ordinal()).add(stobskipiramidi);

        Marker sedemteezera = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.204016804208415, 23.312618510717023))
                        .title("Седемте рилски езера")
                        .snippet("пл. Рила, обл. Кюстендил")
        );
        sedemteezera.setTag("sedemteezera");
        markers.get(Oblasti.Kiustendil.ordinal()).add(sedemteezera);

        Marker vasillevski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.130146729779725, 24.717649412108543))
                        .title("Музей „Васил Левски“")
                        .snippet("гр. Ловеч, обл. Ловеч")
        );
        vasillevski.setTag("vasillevski");
        markers.get(Oblasti.Lovech.ordinal()).add(vasillevski);

        Marker kukrinskohanche = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.12819081977159, 24.884447478221677))
                        .title("Къкринско ханче")
                        .snippet("с. Къкрина, обл. Ловеч")
        );
        kukrinskohanche.setTag("kukrinskohanche");
        markers.get(Oblasti.Lovech.ordinal()).add(kukrinskohanche);

        Marker devetashkapeshtera = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.23360952124564, 24.885529998619834))
                        .title("Деветашка пещера")
                        .snippet("с. Деветаки, обл. Ловеч")
        );
        devetashkapeshtera.setTag("devetashkapeshtera");
        markers.get(Oblasti.Lovech.ordinal()).add(devetashkapeshtera);

        Marker krushunskivodopadi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.24327622043123, 25.033308910266356))
                        .title("Крушунски водопади")
                        .snippet("с. Крушуна, обл. Ловеч")
        );
        krushunskivodopadi.setTag("krushunskivodopadi");
        markers.get(Oblasti.Lovech.ordinal()).add(krushunskivodopadi);

        Marker peshterendom = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.164506124520464, 24.070259219554906))
                        .title("Национален пещерен дом")
                        .snippet("с. Карлуково, обл. Ловеч")
        );
        peshterendom.setTag("peshterendom");
        markers.get(Oblasti.Lovech.ordinal()).add(peshterendom);

        Marker troqnskimanastir = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.86198418952642, 24.780907392498317))
                        .title("Троянски манастир „Успение Богородично“")
                        .snippet("гр. Троян, обл. Ловеч")
        );
        troqnskimanastir.setTag("troqnskimanastir");
        markers.get(Oblasti.Lovech.ordinal()).add(troqnskimanastir);

        Marker muzeicherniosum = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.84120650664696, 24.768139123740003))
                        .title("Природонаучен музей Черни Осъм")
                        .snippet("с. Черни Осъм, обл. Ловеч")
        );
        muzeicherniosum.setTag("muzeicherniosum");
        markers.get(Oblasti.Lovech.ordinal()).add(muzeicherniosum);

        Marker muzeiteteven = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.91839168395663, 24.264879098603963))
                        .title("Исторически музей Тетевен")
                        .snippet("гр. Тетевен, обл. Ловеч")
        );
        muzeiteteven.setTag("muzeiteteven");
        markers.get(Oblasti.Lovech.ordinal()).add(muzeiteteven);

        Marker peshterasuevadupka = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.046924079809855, 24.185922985116726))
                        .title("Пещера „Съева дупка“")
                        .snippet("с. Брестница, обл. Ловеч")
        );
        peshterasuevadupka.setTag("peshterasuevadupka");
        markers.get(Oblasti.Lovech.ordinal()).add(peshterasuevadupka);

        Marker muzeiberkovitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.23907385503053, 23.127174121912354))
                        .title("Етнографски музей Берковица")
                        .snippet("гр. Берковица, обл. Монтана")
        );
        muzeiberkovitsa.setTag("muzeiberkovitsa");
        markers.get(Oblasti.Montana.ordinal()).add(muzeiberkovitsa);

        Marker vruhkom = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.17403265745979, 23.052240654439824))
                        .title("Връх Ком")
                        .snippet("гр. Берковица, обл. Монтана")
        );
        vruhkom.setTag("vruhkom");
        markers.get(Oblasti.Montana.ordinal()).add(vruhkom);

        Marker muzeiivanvazov = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.239311778357994, 23.12663581211398))
                        .title("Къща-музей „Иван Вазов“")
                        .snippet("гр. Берковица, обл. Монтана")
        );
        muzeiivanvazov.setTag("muzeiivanvazov");
        markers.get(Oblasti.Montana.ordinal()).add(muzeiivanvazov);

        Marker muzeimontana = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.406160694200594, 23.226978654451575))
                        .title("Регионален исторически музей Монтана")
                        .snippet("гр. Монтана, обл. Монтана")
        );
        muzeimontana.setTag("muzeimontana");
        markers.get(Oblasti.Montana.ordinal()).add(muzeimontana);

        Marker stanislavdospevski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.18656626643316, 24.325245654390542))
                        .title("Къща-музей „Станислав Доспевски“")
                        .snippet("гр. Пазарджик, обл. Пазарджик")
        );
        stanislavdospevski.setTag("stanislavdospevski");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(stanislavdospevski);
        
        Marker  svetabogoroditsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.18741481450749, 24.32520821206137))
                        .title("Катедрална църква „Света Богородица“")
                        .snippet("гр. Пазарджик, обл. Пазарджик")
        );
        svetabogoroditsa.setTag("svetabogoroditsa");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(svetabogoroditsa);

        Marker muzeipazardzhik = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.190879404511804, 24.332406967884747))
                        .title("Регионален исторически музей Пазарджик")
                        .snippet("гр. Пазарджик, обл. Пазарджик")
        );
        muzeipazardzhik.setTag("muzeipazardzhik");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(muzeipazardzhik);

        Marker muzeistamboliiski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.32275575887194, 24.035250785080514))
                        .title("Вила-музей „Александър Стамболийски“")
                        .snippet("с. Славовица, обл. Пазарджик")
        );
        muzeistamboliiski.setTag("muzeistamboliiski");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(muzeistamboliiski);

        Marker oborishte = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.53763305164412, 24.11634645440802))
                        .title("Историческа местност „Оборище“")
                        .snippet("гр. Панагюрище, обл. Пазарджик")
        );
        oborishte.setTag("oborishte");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(oborishte);

        Marker rainaknqginq = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.50684252149393, 24.183701694888025))
                        .title("Къща-музей „Райна Княгиня“")
                        .snippet("гр. Панагюрище, обл. Пазарджик")
        );
        rainaknqginq.setTag("rainaknqginq");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(rainaknqginq);

        Marker peshterasnezhanka = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.00281986201842, 24.27756428321705))
                        .title("Пещера Снежанка")
                        .snippet("гр. Пещера, обл. Пазарджик")
        );
        peshterasnezhanka.setTag("peshterasnezhanka");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(peshterasnezhanka);

        Marker krepostperistera = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.038988935140374, 24.30426345438345))
                        .title("Крепост „Перистера“")
                        .snippet("гр. Пещера, обл. Пазарджик")
        );
        krepostperistera.setTag("krepostperistera");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(krepostperistera);

        Marker fotinskivodopad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.89070151417604, 24.38633156305534))
                        .title("Фотински водопад")
                        .snippet("с. Фотиново, обл. Пазарджик")
        );
        fotinskivodopad.setTag("fotinskivodopad");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(fotinskivodopad);

        Marker muzeibatak = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.943004715168954, 24.2183787967079))
                        .title("Исторически музей Батак")
                        .snippet("гр. Батак, обл. Пазарджик")
        );
        muzeibatak.setTag("muzeibatak");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(muzeibatak);

        Marker trunskozhdrelo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.86155090612777, 22.649758154424124))
                        .title("Трънско ждрело")
                        .snippet("гр. Трън, обл. Перник")
        );
        trunskozhdrelo.setTag("trunskozhdrelo");
        markers.get(Oblasti.Pernik.ordinal()).add(trunskozhdrelo);

        Marker muzeipernik = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.60956343362626, 23.029476300436407))
                        .title("Подземен минен музей Перник")
                        .snippet("гр. Перник, обл. Перник")
        );
        muzeipernik.setTag("muzeipernik");
        markers.get(Oblasti.Pernik.ordinal()).add(muzeipernik);

        Marker georgipobedonosets = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.40750967481925, 24.619601212122397))
                        .title("Параклис-мавзолей „Св. Георги Победоносец“")
                        .snippet("гр. Плевен, обл. Плевен")
        );
        georgipobedonosets.setTag("georgipobedonosets");
        markers.get(Oblasti.Pleven.ordinal()).add(georgipobedonosets);

        Marker plevenskaepopeq = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.398883520939705, 24.606535254451227))
                        .title("Панорама „Плевенска епопея“")
                        .snippet("гр. Плевен, обл. Плевен")
        );
        plevenskaepopeq.setTag("plevenskaepopeq");
        markers.get(Oblasti.Pleven.ordinal()).add(plevenskaepopeq);

        Marker muzeipleven = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.40467263878741, 24.61807812376838))
                        .title("Регионален исторически музей Плевен")
                        .snippet("гр. Плевен, обл. Плевен")
        );
        muzeipleven.setTag("muzeipleven");
        markers.get(Oblasti.Pleven.ordinal()).add(muzeipleven);

        Marker muzeiplovdiv = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.15153657400798, 24.745425785072012))
                        .title("Регионален исторически музей Пловдив")
                        .snippet("гр. Пловдив, обл. Пловдив")
        );
        muzeiplovdiv.setTag("muzeiplovdiv");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeiplovdiv);

        Marker etnografskimuzeiplovdiv = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.150127056113355, 24.753198196718))
                        .title("Регионален етнографски музей Пловдив")
                        .snippet("гр. Пловдив, обл. Пловдив")
        );
        etnografskimuzeiplovdiv.setTag("etnografskimuzeiplovdiv");
        markers.get(Oblasti.Plovdiv.ordinal()).add(etnografskimuzeiplovdiv);

        Marker antichenteatur = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.146981326801026, 24.751079925553256))
                        .title("Античен театър Пловдив")
                        .snippet("гр. Пловдив, обл. Пловдив")
        );
        antichenteatur.setTag("antichenteatur");
        markers.get(Oblasti.Plovdiv.ordinal()).add(antichenteatur);

        Marker kushtahindliqn = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.14995435095665, 24.75115360836411))
                        .title("Къща-музей Хиндлиян")
                        .snippet("гр. Пловдив, обл. Пловдив")
        );
        kushtahindliqn.setTag("kushtahindliqn");
        markers.get(Oblasti.Plovdiv.ordinal()).add(kushtahindliqn);

        Marker rimskistadion = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.14764346191052, 24.74812907423321))
                        .title("Римски стадион Пловдив")
                        .snippet("гр. Пловдив, обл. Пловдив")
        );
        rimskistadion.setTag("rimskistadion");
        markers.get(Oblasti.Plovdiv.ordinal()).add(rimskistadion);

        Marker muzeiperushtitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.054867729091235, 24.545375954384088))
                        .title("Исторически музей Перущица")
                        .snippet("гр. Перущица, обл. Пловдив")
        );
        muzeiperushtitsa.setTag("muzeiperushtitsa");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeiperushtitsa);

        Marker muzeiivanvazovsopot = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.65426457961274, 24.75615491208465))
                        .title("Къща-музей „Иван Вазов“")
                        .snippet("гр. Сопот, обл. Пловдив")
        );
        muzeiivanvazovsopot.setTag("muzeiivanvazovsopot");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeiivanvazovsopot);

        Marker vuvedeniebogorodichno = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.65580054725851, 24.759001997880567))
                        .title("Женски метох „Въведение Богородично“")
                        .snippet("гр. Сопот, обл. Пловдив")
        );
        vuvedeniebogorodichno.setTag("vuvedeniebogorodichno");
        markers.get(Oblasti.Plovdiv.ordinal()).add(vuvedeniebogorodichno);

        Marker muzeivasillevski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.642819388125886, 24.80286320838859))
                        .title("Национален музей „Васил Левски“")
                        .snippet("гр. Карлово, обл. Пловдив")
        );
        muzeivasillevski.setTag("muzeivasillevski");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeivasillevski);

        Marker muzeikarlovo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.63676628475825, 24.806484458108464))
                        .title("Исторически музей Карлово")
                        .snippet("гр. Карлово, обл. Пловдив")
        );
        muzeikarlovo.setTag("muzeikarlovo");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeikarlovo);

        Marker starinnokarlovo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.637895268385726, 24.80575861023609))
                        .title("Комплекс Старинно Карлово")
                        .snippet("гр. Карлово, обл. Пловдив")
        );
        starinnokarlovo.setTag("starinnokarlovo");
        markers.get(Oblasti.Plovdiv.ordinal()).add(starinnokarlovo);

        Marker vruhkadrafil = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.5212520274865, 24.98898828509036))
                        .title("Връх Кадрафил (гроба на Хаджи Димитър)")
                        .snippet("с. Свежен, обл. Пловдив")
        );
        vruhkadrafil.setTag("vruhkadrafil");
        markers.get(Oblasti.Plovdiv.ordinal()).add(vruhkadrafil);

        Marker muzeihristobotev = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.6112455235945, 24.977401954411736))
                        .title("Национален музей „Христо Ботев“")
                        .snippet("гр. Калофер, обл. Пловдив")
        );
        muzeihristobotev.setTag("muzeihristobotev");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeihristobotev);

        Marker vruhbotev = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.71797193819244, 24.91645197316995))
                        .title("Връх Ботев")
                        .snippet("Стара планина, обл. Пловдив")
        );
        vruhbotev.setTag("vruhbotev");
        markers.get(Oblasti.Plovdiv.ordinal()).add(vruhbotev);

        Marker raiskotopruskalo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.70134623264146, 24.925576971605476))
                        .title("Водопад „Райското пръскало“")
                        .snippet("гр. Калофер, обл. Пловдив")
        );
        raiskotopruskalo.setTag("raiskotopruskalo");
        markers.get(Oblasti.Plovdiv.ordinal()).add(raiskotopruskalo);

        Marker episkopskabazilika = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.56684777811788, 23.280275071549493))
                        .title("Епископска базилика")
                        .snippet("гр. Сандански, обл. Благоевград")
        );
        episkopskabazilika.setTag("episkopskabazilika");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(episkopskabazilika);

        Marker muzeisandanski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.56635940708962, 23.280708839018583))
                        .title("Археологически музей Сандански")
                        .snippet("гр. Сандански, обл. Благоевград")
        );
        muzeisandanski.setTag("muzeisandanski");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(muzeisandanski);

        Marker asenovakrepost = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.986422894593176, 24.872907083216212))
                        .title("Асенова крепост")
                        .snippet("гр. Асеновград, обл. Пловдив")
        );
        asenovakrepost.setTag("asenovakrepost");
        markers.get(Oblasti.Plovdiv.ordinal()).add(asenovakrepost);

        Marker muzeiasenovgrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.006585733205405, 24.874241308357067))
                        .title("Исторически музей Асеновград")
                        .snippet("гр. Асеновград, обл. Пловдив")
        );
        muzeiasenovgrad.setTag("muzeiasenovgrad");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeiasenovgrad);

        Marker bachkovskimanastir = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.94229547047725, 24.84941115272292))
                        .title("Бачковски манастир")
                        .snippet("с. Бачково, обл. Пловдив")
        );
        bachkovskimanastir.setTag("bachkovskimanastir");
        markers.get(Oblasti.Plovdiv.ordinal()).add(bachkovskimanastir);

        Marker abritus = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.52210928509673, 26.552489183292835))
                        .title("Археологически резерват „Абритус“")
                        .snippet("гр. Разград, обл. Разград")
        );
        abritus.setTag("abritus");
        markers.get(Oblasti.Razgrad.ordinal()).add(abritus);

        Marker muzeiisperih = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.70906623507979, 26.82603284282088))
                        .title("Исторически музей Исперих")
                        .snippet("гр. Исперих, обл. Разград")
        );
        muzeiisperih.setTag("muzeiisperih");
        markers.get(Oblasti.Razgrad.ordinal()).add(muzeiisperih);

        Marker rezervatsborqnovo  = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.74094813268565, 26.760114639127035))
                        .title("Историко-археологически резерват „Сборяново“ и Хелис")
                        .snippet("с. Свещари, обл. Разград")
        );
        rezervatsborqnovo.setTag("rezervatsborqnovo");
        markers.get(Oblasti.Razgrad.ordinal()).add(rezervatsborqnovo);

        Marker demirbabateke = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.7387403964216, 26.75210598515165))
                        .title("Демир Баба теке")
                        .snippet("с. Свещари, обл. Разград")
        );
        demirbabateke.setTag("demirbabateke");
        markers.get(Oblasti.Razgrad.ordinal()).add(demirbabateke);

        Marker zaharistoqnov = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.851652284102755, 25.949909513992782))
                        .title("Къща-музей „Захари Стоянов“")
                        .snippet("гр. Русе, обл. Русе")
        );
        zaharistoqnov.setTag("zaharistoqnov");
        markers.get(Oblasti.Ruse.ordinal()).add(zaharistoqnov);

        Marker panteonruse = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.85069167884785, 25.960816712144993))
                        .title("Пантеон на възрожденците")
                        .snippet("гр. Русе, обл. Русе")
        );
        panteonruse.setTag("panteonruse");
        markers.get(Oblasti.Ruse.ordinal()).add(panteonruse);

        Marker ivanovskitsurkvi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.69501194422358, 25.98703764097232))
                        .title("Ивановски скални църкви")
                        .snippet("с. Иваново, обл. Русе")
        );
        ivanovskitsurkvi.setTag("ivanovskitsurkvi");
        markers.get(Oblasti.Ruse.ordinal()).add(ivanovskitsurkvi);

        Marker svetidimitriibasarbovski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.766970146167814, 25.96457962563444))
                        .title("Скален Манастир „Свети Димитрий Басарбовски“")
                        .snippet("с. Басарбово, обл. Русе")
        );
        svetidimitriibasarbovski.setTag("svetidimitriibasarbovski");
        markers.get(Oblasti.Ruse.ordinal()).add(svetidimitriibasarbovski);

        Marker muzeibqla = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.465795106623396, 25.732415315310284))
                        .title("Исторически музей Бяла")
                        .snippet("гр. Бяла, обл. Русе")
        );
        muzeibqla.setTag("muzeibqla");
        markers.get(Oblasti.Ruse.ordinal()).add(muzeibqla);

        Marker svetamarina = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.47669341073777, 26.007295427467533))
                        .title("Манастир „Света Марина“")
                        .snippet("с. Каран Върбовка, обл. Русе")
        );
        svetamarina.setTag("svetamarina");
        markers.get(Oblasti.Ruse.ordinal()).add(svetamarina);

        Marker madzhiditabiq = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(44.095289987211125, 27.24940476798045))
                        .title("Крепост „Меджиди Табия“")
                        .snippet("гр. Силистра, обл. Силистра")
        );
        madzhiditabiq.setTag("madzhiditabiq");
        markers.get(Oblasti.Silistra.ordinal()).add(madzhiditabiq);

        Marker muzeisreburna = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(44.10369663232772, 27.062540155256027))
                        .title("Природонаучен музей Сребърна")
                        .snippet("с. Сребърна, обл. Силистра")
        );
        muzeisreburna.setTag("muzeisreburna");
        markers.get(Oblasti.Silistra.ordinal()).add(muzeisreburna);

        Marker voennogrobishte = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.97739317929452, 26.624587613439623))
                        .title("Мемориален комплекс „Военна гробница – 1916 г.“")
                        .snippet("с. Шуменци, обл. Силистра")
        );
        voennogrobishte.setTag("voennogrobishte");
        markers.get(Oblasti.Silistra.ordinal()).add(voennogrobishte);

        Marker muzeitutrakan = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(44.049526850829075, 26.607960954484223))
                        .title("Етнографски музей „Дунавски риболов и лодкостроене“")
                        .snippet("гр. Тутракан, обл. Силистра")
        );
        muzeitutrakan.setTag("muzeitutrakan");
        markers.get(Oblasti.Silistra.ordinal()).add(muzeitutrakan);

        Marker hadzhidimitar = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.67858140859561, 26.311808383250412))
                        .title("Къща музей „Хаджи Димитър“")
                        .snippet("гр. Сливен, обл. Сливен")
        );
        hadzhidimitar.setTag("hadzhidimitar");
        markers.get(Oblasti.Sliven.ordinal()).add(hadzhidimitar);

        Marker muzeisliven = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.689409076861814, 26.317398383251014))
                        .title("Национален музей на текстилната индустрия")
                        .snippet("гр. Сливен, обл. Сливен")
        );
        muzeisliven.setTag("muzeisliven");
        markers.get(Oblasti.Sliven.ordinal()).add(muzeisliven);

        Marker dimitardobrovich = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.68061041604176, 26.319596710238187))
                        .title("Художествена галерия „Димитър Добрович“")
                        .snippet("гр. Сливен, обл. Сливен")
        );
        dimitardobrovich.setTag("dimitardobrovich");
        markers.get(Oblasti.Sliven.ordinal()).add(dimitardobrovich);

        Marker muzeivelingrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.04389868208946, 23.98656459116949))
                        .title("Исторически музей Велинград")
                        .snippet("гр. Велинград, обл. Пазарджик")
        );
        muzeivelingrad.setTag("muzeivelingrad");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(muzeivelingrad);

        Marker panteonsliven = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.888825088762424, 26.44700491024857))
                        .title("Пантеон на Георги Стойков Раковски и музей на котленските възрожденци")
                        .snippet("гр. Котел, обл. Сливен")
        );
        panteonsliven.setTag("panteonsliven");
        markers.get(Oblasti.Sliven.ordinal()).add(panteonsliven);

        Marker muzeikotel = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.88845182238774, 26.429789012096315))
                        .title("Природонаучен музей Котел")
                        .snippet("гр. Котел, обл. Сливен")
        );
        muzeikotel.setTag("muzeikotel");
        markers.get(Oblasti.Sliven.ordinal()).add(muzeikotel);

        Marker rezervatzheravna = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.83275635006746, 26.457546567916726))
                        .title("Архитектурно-исторически резерват Жеравна")
                        .snippet("с. Жеравна, обл. Сливен")
        );
        rezervatzheravna.setTag("rezervatzheravna");
        markers.get(Oblasti.Sliven.ordinal()).add(rezervatzheravna);

        Marker iordaniovkovzheravna = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.83269855765409, 26.457187839081183))
                        .title("Къща музей „Йордан Йовков“")
                        .snippet("с. Жеравна, обл. Сливен")
        );
        iordaniovkovzheravna.setTag("iordaniovkovzheravna");
        markers.get(Oblasti.Sliven.ordinal()).add(iordaniovkovzheravna);

        Marker muzeisofiq = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.65546108923515, 23.27081752373079))
                        .title("Национален исторически музей")
                        .snippet("гр. София, обл. София-град")
        );
        muzeisofiq.setTag("muzeisofiq");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(muzeisofiq);

        Marker boqnskacherkva = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.64470339725552, 23.26614008140111))
                        .title("Боянска църква")
                        .snippet("гр. София, обл. София-град")
        );
        boqnskacherkva.setTag("boqnskacherkva");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(boqnskacherkva);

        Marker svetialeksandurnevski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.695936968125885, 23.332894254415937))
                        .title("Храм-паметник „Свети Александър Невски“")
                        .snippet("гр. София, обл. София-град")
        );
        svetialeksandurnevski.setTag("svetialeksandurnevski");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(svetialeksandurnevski);

        Marker voennoistoricheskimuzei = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.687766807092245, 23.350359856263257))
                        .title("Национален военноисторически музей")
                        .snippet("гр. София, обл. София-град")
        );
        voennoistoricheskimuzei.setTag("voennoistoricheskimuzei");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(voennoistoricheskimuzei);

        Marker muzeigotsedelchev = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.5730665610731, 23.726823337171158))
                        .title("Исторически музей Гоце Делчев")
                        .snippet("гр. Гоце Делчев, обл. Благоевград")
        );
        muzeigotsedelchev.setTag("muzeigotsedelchev");
        markers.get(Oblasti.Blagoevgrad.ordinal()).add(muzeigotsedelchev);

        Marker daskalovnitsata = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.92866615060895, 25.87635686792132))
                        .title("Архитектурно-исторически комплекс „Даскалоливницата“")
                        .snippet("гр. Елена, обл. Велико Търново")
        );
        daskalovnitsata.setTag("daskalovnitsata");
        markers.get(Oblasti.VTurnovo.ordinal()).add(daskalovnitsata);

        Marker ilarionmakariopolski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.93049608010861, 25.879691110250757))
                        .title("Къща-музей „Иларион Макариополски“")
                        .snippet("гр. Елена, обл. Велико Търново")
        );
        ilarionmakariopolski.setTag("ilarionmakariopolski");
        markers.get(Oblasti.VTurnovo.ordinal()).add(ilarionmakariopolski);

        Marker zemqtaihorata = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.680217326798974, 23.320773127427408))
                        .title("Национален музей „Земята и хората“")
                        .snippet("гр. София, обл. София-град")
        );
        zemqtaihorata.setTag("zemqtaihorata");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(zemqtaihorata);

        Marker dvoretsnakulturata = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.68516178441126, 23.31928700044006))
                        .title("Национален дворец на културата")
                        .snippet("гр. София, обл. София-град")
        );
        dvoretsnakulturata.setTag("dvoretsnakulturata");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(dvoretsnakulturata);

        Marker galeriqsofiq = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.696848226149186, 23.32727993579925))
                        .title("Национална художествена галерия")
                        .snippet("гр. София, обл. София-град")
        );
        galeriqsofiq.setTag("galeriqsofiq");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(galeriqsofiq);

        Marker etnografskiinstitut = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.696543742755786, 23.327190940922108))
                        .title("Етнографски институт с музей при БАН")
                        .snippet("гр. София, обл. София-град")
        );
        etnografskiinstitut.setTag("etnografskiinstitut");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(etnografskiinstitut);

        Marker voinishkiqtpametnik = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.8470243675405, 22.977530356271245))
                        .title("Първият войнишки паметник")
                        .snippet("с. Алдомировци, обл. София")
        );
        voinishkiqtpametnik.setTag("voinishkiqtpametnik");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(voinishkiqtpametnik);

        Marker muzeientropole = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.83062822095654, 23.993978110245667))
                        .title("Исторически музей Ентрополе")
                        .snippet("гр. Ентрополе, обл. София")
        );
        muzeientropole.setTag("muzeientropole");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(muzeientropole);

        Marker chasovnikovakula = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.82990615851449, 23.99276265257486))
                        .title("Часовникова кула Ентрополе")
                        .snippet("гр. Ентрополе, обл. София")
        );
        chasovnikovakula.setTag("chasovnikovakula");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(chasovnikovakula);

        Marker etropolskimanastir = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.82375584492935, 24.036625352574603))
                        .title("Етрополски манастир „Св. Троица“")
                        .snippet("гр. Ентрополе, обл. София")
        );
        etropolskimanastir.setTag("etropolskimanastir");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(etropolskimanastir);

        Marker svpeturipavel = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.6977235086056, 23.295769154415975))
                        .title("храм „Св. Петър и Павел“")
                        .snippet("гр. София, обл. София-град")
        );
        svpeturipavel.setTag("svpeturipavel");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(svpeturipavel);

        Marker zoopark = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.658082622643235, 23.328635654414143))
                        .title("Зоологическа градина София")
                        .snippet("гр. София, обл. София-град")
        );
        zoopark.setTag("zoopark");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(zoopark);

        Marker muzeipriban = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.69575100692255, 23.32853026975751))
                        .title("Национален природонаучен музей при БАН")
                        .snippet("гр. София, обл. София-град")
        );
        muzeipriban.setTag("muzeipriban");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(muzeipriban);

        Marker muzeinasporta = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.68815466133288, 23.334649539073965))
                        .title("Музей на историята на физическата култура и спорта в Нац. стадион „В. Левски“")
                        .snippet("гр. София, обл. София-град")
        );
        muzeinasporta.setTag("muzeinasporta");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(muzeinasporta);

        Marker antropologichenmuzei = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.67950330707435, 23.354265777707223))
                        .title("Национален антропологичен музей")
                        .snippet("гр. София, обл. София-град")
        );
        antropologichenmuzei.setTag("antropologichenmuzei");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(antropologichenmuzei);

        Marker trakiiskagrobnitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.512039126098735, 24.547261844608414))
                        .title("Тракийска гробница")
                        .snippet("с. Старосел, обл. Пловдив")
        );
        trakiiskagrobnitsa.setTag("trakiiskagrobnitsa");
        markers.get(Oblasti.Plovdiv.ordinal()).add(trakiiskagrobnitsa);

        Marker rimskagrobnitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.49566771769516, 24.702858231673357))
                        .title("Римска гробница")
                        .snippet("гр. Хисаря, обл. Пловдив")
        );
        rimskagrobnitsa.setTag("rimskagrobnitsa");
        markers.get(Oblasti.Plovdiv.ordinal()).add(rimskagrobnitsa);

        Marker arheologicheskiinstitut = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.69652140060575, 23.32438085256816))
                        .title("Национален археологически институт с музей при БАН")
                        .snippet("гр. София, обл. София-град")
        );
        arheologicheskiinstitut.setTag("arheologicheskiinstitut");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(arheologicheskiinstitut);

        Marker politehnicheskimuzei = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.70415861916992, 23.31195088325177))
                        .title("Национален политехнически музей")
                        .snippet("гр. София, обл. София-град")
        );
        politehnicheskimuzei.setTag("politehnicheskimuzei");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(politehnicheskimuzei);

        Marker muzeichiprovtsi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.38356162462843, 22.878899554450438))
                        .title("Исторически музей Чипровци")
                        .snippet("гр. Чипровци, обл. Монтана")
        );
        muzeichiprovtsi.setTag("muzeichiprovtsi");
        markers.get(Oblasti.Montana.ordinal()).add(muzeichiprovtsi);

        Marker svetiivanrilski = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.404026134495616, 22.93372072561608))
                        .title("Чипровски манастир „Свети Иван Рилски“")
                        .snippet("гр. Чипровци, обл. Монтана")
        );
        svetiivanrilski.setTag("svetiivanrilski");
        markers.get(Oblasti.Montana.ordinal()).add(svetiivanrilski);

        Marker muzeibratsigovo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.02296652422717, 24.37465355807813))
                        .title("Градски исторически музей Брацигово")
                        .snippet("гр. Брацигово, обл. Пазарджик")
        );
        muzeibratsigovo.setTag("muzeibratsigovo");
        markers.get(Oblasti.Pazardzhik.ordinal()).add(muzeibratsigovo);

        Marker peioqvorov = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.20215281878847, 25.326690325555983))
                        .title("Къща-музей „Пейо Яворов“")
                        .snippet("гр. Чирпан, обл. Стара Загора")
        );
        peioqvorov.setTag("peioqvorov");
        markers.get(Oblasti.StZagora.ordinal()).add(peioqvorov);

        Marker svetiatanasii = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.10596938763065, 25.42436866788049))
                        .title("Златноливаденски манастир „Свети Атанасий“")
                        .snippet("с. Златна ливада, обл. Стара Загора")
        );
        svetiatanasii.setTag(svetiatanasii);
        markers.get(Oblasti.StZagora.ordinal()).add(svetiatanasii);

        Marker monumenthaskovo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.928464129977996, 25.55401958136558))
                        .title("Монумент „Света Богородица с Младенеца“")
                        .snippet("гр. Хасково, обл. Хасково")
        );
        monumenthaskovo.setTag("monumenthaskovo");
        markers.get(Oblasti.Haskovo.ordinal()).add(monumenthaskovo);

        Marker aleksandrovskagrobnitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.97965149799914, 25.738768110203477))
                        .title("Александровска гробница")
                        .snippet("с. Александрово, обл. Хасково")
        );
        aleksandrovskagrobnitsa.setTag("aleksandrovskagrobnitsa");
        markers.get(Oblasti.Haskovo.ordinal()).add(aleksandrovskagrobnitsa);

        Marker grobnitsamezek = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.735382119014226, 26.101739281355968))
                        .title("Тракийска куполна гробница")
                        .snippet("с. Мезек, обл. Хасково")
        );
        grobnitsamezek.setTag("trakiiskagrobnitsa");
        markers.get(Oblasti.Haskovo.ordinal()).add(grobnitsamezek);

        Marker mezeshkakrepost = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.73776106482963, 26.0839772636074))
                        .title("Мезешка крепост")
                        .snippet("с. Мезек, обл. Хасково")
        );
        mezeshkakrepost.setTag("mezeshkakrepost");
        markers.get(Oblasti.Haskovo.ordinal()).add(mezeshkakrepost);

        Marker vilaarmira = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.49918776859665, 26.10634551387549))
                        .title("Антична римска „Вила Армира“")
                        .snippet("гр. Ивайловград, обл. Хасково")
        );
        vilaarmira.setTag("vilaarmira");
        markers.get(Oblasti.Haskovo.ordinal()).add(vilaarmira);

        Marker muzeiivailovgrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.52869893048042, 26.125663966004435))
                        .title("Исторически музей Ивайловград")
                        .snippet("гр. Ивайловград, обл. Хасково")
        );
        muzeiivailovgrad.setTag("muzeiivailovgrad");
        markers.get(Oblasti.Haskovo.ordinal()).add(muzeiivailovgrad);

        Marker iztochnirodopi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.6435624815288, 25.87184670833931))
                        .title("Природозащитен център „Източни Родопи“")
                        .snippet("гр. Маджарово, обл. Хасково")
        );
        iztochnirodopi.setTag("iztochnirodopi");
        markers.get(Oblasti.Haskovo.ordinal()).add(iztochnirodopi);

        Marker muzeidimitrovgrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.057176372914114, 25.59645559228069))
                        .title("Исторически музей Димитровград")
                        .snippet("гр. Димитровград, обл. Хасково")
        );
        muzeidimitrovgrad.setTag("muzeidimitrovgrad");
        markers.get(Oblasti.Haskovo.ordinal()).add(muzeidimitrovgrad);

        Marker peniopenev = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.056348343521115, 25.59242649572132))
                        .title("Дом-музей „Пеньо Пенев“")
                        .snippet("гр. Димитровград, обл. Хасково")
        );
        peniopenev.setTag("peniopenev");
        markers.get(Oblasti.Haskovo.ordinal()).add(peniopenev);

        Marker dzhordanobruno = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.04607918468038, 25.5780233697252))
                        .title("Народна астрономическа обсерватория и планетариум „Джордано Бруно“")
                        .snippet("гр. Димитровград, обл. Хасково")
        );
        dzhordanobruno.setTag("dzhordanobruno");
        markers.get(Oblasti.Haskovo.ordinal()).add(dzhordanobruno);

        Marker hizhaaleko = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.582691266695186, 23.292126754410226))
                        .title("Хижа Алеко")
                        .snippet("пл. Витоша, обл. София-град")
        );
        hizhaaleko.setTag("hizhaaleko");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(hizhaaleko);

        Marker chernivruh = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.56413680093256, 23.278484080437977))
                        .title("Черни връх")
                        .snippet("пл. Витоша, обл. София-град")
        );
        chernivruh.setTag("chernivruh");
        markers.get(Oblasti.SofiaGrad.ordinal()).add(chernivruh);

        Marker rezervatkoprivshtitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.636311986612476, 24.360077463778577))
                        .title("Архитектурно-исторически резерват Копривщица")
                        .snippet("гр. Копривщица, обл. София")
        );
        rezervatkoprivshtitsa.setTag("rezervatkoprivshtitsa");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(rezervatkoprivshtitsa);

        Marker muzeinovazagora = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.491568871060714, 26.01239539858268))
                        .title("Исторически музей „Каранова могила“")
                        .snippet("гр. Нова Загора, обл. Сливен")
        );
        muzeinovazagora.setTag("muzeinovazagora");
        markers.get(Oblasti.Sliven.ordinal()).add(muzeinovazagora);

        Marker svetigeorgi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.351212176100255, 25.96449692556344))
                        .title("Храм-паметник „Свети Георги“")
                        .snippet("с. Любенова махала, обл. Сливен")
        );
        svetigeorgi.setTag("svetigeorgi");
        markers.get(Oblasti.Sliven.ordinal()).add(svetigeorgi);

        Marker muzeiklisura = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.694248324831115, 24.449771450720387))
                        .title("Исторически музей Клисура")
                        .snippet("гр. Клисура, обл. Пловдив")
        );
        muzeiklisura.setTag("muzeiklisura");
        markers.get(Oblasti.Plovdiv.ordinal()).add(muzeiklisura);

        Marker sedemteprestola = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.999963523217026, 23.472574323747914))
                        .title("Манастир „Седемте престола“")
                        .snippet("с. Осеновлаг, обл. София")
        );
        sedemteprestola.setTag("sedemteprestola");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(sedemteprestola);

        Marker muzeisamokov = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.33938136656388, 23.560204881385935))
                        .title("Исторически музей Самоков")
                        .snippet("гр. Самоков, обл. София")
        );
        muzeisamokov.setTag("muzeisamokov");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(muzeisamokov);

        Marker pokrovbogorodichen = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.33129476289921, 23.55978645255004))
                        .title("Девически манастир „Покров Богородичен“")
                        .snippet("гр. Самоков, обл. София")
        );
        pokrovbogorodichen.setTag("pokrovbogorodichen");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(pokrovbogorodichen);

        Marker tsarimaligrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.35193804597295, 23.382222539057196))
                        .title("Музеен комплекс „Цари Мали град“")
                        .snippet("с. Белчин, обл. София")
        );
        tsarimaligrad.setTag("tsarimaligrad");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(tsarimaligrad);

        Marker traqnovivrata = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.35661739667653, 23.918332949589434))
                        .title("Проход „Траянови врата“")
                        .snippet("гр. Ихтиман, обл. София")
        );
        traqnovivrata.setTag("traqnovivrata");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(traqnovivrata);

        Marker kostenskivodopad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.24904719817689, 23.805233323710482))
                        .title("Костенски водопад")
                        .snippet("с. Костенец, обл. София")
        );
        kostenskivodopad.setTag("kostenskivodopad");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(kostenskivodopad);

        Marker vruhmusala = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.179512057794575, 23.584952057399583))
                        .title("Връх Мусала")
                        .snippet("пл. Рила, обл. София")
        );
        vruhmusala.setTag("vruhmusala");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(vruhmusala);

        Marker slaveikovouchilishte = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.24212492688331, 26.57152281211405))
                        .title("Исторически музей „Славейковото училище“")
                        .snippet("гр. Търговище, обл. Търговище")
        );
        slaveikovouchilishte.setTag("slaveikovouchilishte");
        markers.get(Oblasti.Turgovishte.ordinal()).add(slaveikovouchilishte);

        Marker krepostmisionis = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.19812708949189, 26.512625283276506))
                        .title("Средновековна крепост „Мисионис“")
                        .snippet("гр. Търговище, обл. Търговище")
        );
        krepostmisionis.setTag("krepostmisionis");
        markers.get(Oblasti.Turgovishte.ordinal()).add(krepostmisionis);

        Marker pametnikskravena = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.952185882865635, 23.76848708141647))
                        .title("Паметник костница на Ботевите четници")
                        .snippet("с. Скравена, обл. София")
        );
        pametnikskravena.setTag("pametnikskravena");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(pametnikskravena);

        Marker kulabotevgrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.908092662286734, 23.792498681414177))
                        .title("Часовникова кула Ботевград")
                        .snippet("гр. Ботевград, обл. София")
        );
        kulabotevgrad.setTag("kulabotevgrad");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(kulabotevgrad);

        Marker muzeibotevgrad = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.906855274322744, 23.79277779675566))
                        .title("Исторически музей Ботевград")
                        .snippet("гр. Ботевград, обл. София")
        );
        muzeibotevgrad.setTag("muzeibotevgrad");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(muzeibotevgrad);

        Marker elinpelin = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.655554484864744, 23.821970240920106))
                        .title("Къща-музей „Елин Пелин“")
                        .snippet("с. Байлово, обл. София")
        );
        elinpelin.setTag("elinpelin");
        markers.get(Oblasti.SofiaOblast.ordinal()).add(elinpelin);

        Marker muzeismolqn = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.57634737586375, 24.714873713879218))
                        .title("Регионален исторически музей „Стою Шишков“")
                        .snippet("гр. Смолян, обл. Смолян")
        );
        muzeismolqn.setTag("muzeismolqn");
        markers.get(Oblasti.Smolqn.ordinal()).add(muzeismolqn);

        Marker observatoriqsmolqn = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.69667245782743, 24.742068385049848))
                        .title("Национална астрономическа обсерватория „Рожен“")
                        .snippet("гр. Смолян, обл. Смолян")
        );
        observatoriqsmolqn.setTag("observatoriqsmolqn");
        markers.get(Oblasti.Smolqn.ordinal()).add(observatoriqsmolqn);

        Marker hrammomchilovtsi = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.14981018541818, 24.75280502185789))
                        .title("Храм „Св. св. Константин и Елена“")
                        .snippet("с. Момчиловци, обл. Смолян")
        );
        hrammomchilovtsi.setTag("hrammomchilovtsi");
        markers.get(Oblasti.Smolqn.ordinal()).add(hrammomchilovtsi);

        Marker peshterauhlovitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.512640308169765, 24.661798454357594))
                        .title("Пещера Ухловица")
                        .snippet("с. Могилица, обл. Смолян")
        );
        peshterauhlovitsa.setTag("peshterauhlovitsa");
        markers.get(Oblasti.Smolqn.ordinal()).add(peshterauhlovitsa);

        Marker vruhsnezhanka = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.63808870709629, 24.675593335358084))
                        .title("Връх Снежанка")
                        .snippet("пм. Пампорово, обл. Смолян")
        );
        vruhsnezhanka.setTag("vruhsnezhanka");
        markers.get(Oblasti.Smolqn.ordinal()).add(vruhsnezhanka);

        Marker vruhgolqmperelik = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.606004905110915, 24.574378840868278))
                        .title("Връх Голям Перелик")
                        .snippet("пл. Родопи, обл. Смолян")
        );
        vruhgolqmperelik.setTag("vruhgolqmperelik");
        markers.get(Oblasti.Smolqn.ordinal()).add(vruhgolqmperelik);

        Marker chudnitemostove = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.81957602763688, 24.582251796701836))
                        .title("Природна забележителност „Чудните мостове“")
                        .snippet("пл. Родопи, обл. Смолян")
        );
        chudnitemostove.setTag("chudnitemostove");
        markers.get(Oblasti.Smolqn.ordinal()).add(chudnitemostove);

        Marker komplekszlatograd = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.38755226735685, 25.08630653900995))
                        .title("Етнографски ареален комплекс „Златоград”")
                        .snippet("гр. Златоград, обл. Смолян")
        );
        komplekszlatograd.setTag("komplekszlatograd");
        markers.get(Oblasti.Smolqn.ordinal()).add(komplekszlatograd);

        Marker rodopskikristal = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.498399741238565, 24.93968031202768))
                        .title("Кристална зала „Родопски кристал”")
                        .snippet("гр. Мадан, обл. Смолян")
        );
        rodopskikristal.setTag("rodopskikristal");
        markers.get(Oblasti.Smolqn.ordinal()).add(rodopskikristal);

        Marker peshterasharenka = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.489734134471156, 24.917959213874926))
                        .title("Пещера Шаренка")
                        .snippet("гр. Мадан, обл. Смолян")
        );
        peshterasharenka.setTag("peshterasharenka");
        markers.get(Oblasti.Smolqn.ordinal()).add(peshterasharenka);

        Marker trigradskozhdrelo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.61510453781754, 24.380009276280887))
                        .title("Триградско ждрело")
                        .snippet("пл. Родопи, обл. Смолян")
        );
        trigradskozhdrelo.setTag("trigradskozhdrelo");
        markers.get(Oblasti.Smolqn.ordinal()).add(trigradskozhdrelo);

        Marker buinovskozhdrelo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.6353707827918, 24.336561969705144))
                        .title("Буйновско ждрело")
                        .snippet("пл. Родопи, обл. Смолян")
        );
        buinovskozhdrelo.setTag("buinovskozhdrelo");
        markers.get(Oblasti.Smolqn.ordinal()).add(buinovskozhdrelo);

        Marker qgodinskapeshtera = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.62897088168129, 24.330063427375602))
                        .title("Ягодинска пещера")
                        .snippet("пл. Родопи, обл. Смолян")
        );
        qgodinskapeshtera.setTag("qgodinskapeshtera");
        markers.get(Oblasti.Smolqn.ordinal()).add(qgodinskapeshtera);

        Marker muzeistarazagora = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.42664606219112, 25.627768039060953))
                        .title("Регионален исторически музей Стара Загора")
                        .snippet("гр. Стара Загора, обл. Стара Загора")
        );
        muzeistarazagora.setTag("muzeistarazagora");
        markers.get(Oblasti.StZagora.ordinal()).add(muzeistarazagora);

        Marker neolitnizhilishta = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.42472216583697, 25.610844098579456))
                        .title("Музей Неолитни жилища")
                        .snippet("гр. Стара Загора, обл. Стара Загора")
        );
        neolitnizhilishta.setTag("neolitnizhilishta");
        markers.get(Oblasti.StZagora.ordinal()).add(neolitnizhilishta);

        Marker kompleksstzagora = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.43340713081667, 25.654269754402833))
                        .title("Мемориален комплекс „Бранителите на Стара Загора“")
                        .snippet("гр. Стара Загора, обл. Стара Загора")
        );
        kompleksstzagora.setTag("kompleksstzagora");
        markers.get(Oblasti.StZagora.ordinal()).add(kompleksstzagora);

        Marker kazanlushkagrobnitsa = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.62595088959452, 25.399142939070774))
                        .title("Казанлъшка гробница")
                        .snippet("гр. Казанлък, обл. Стара Загора")
        );
        kazanlushkagrobnitsa.setTag("kazanlushkagrobnitsa");
        markers.get(Oblasti.StZagora.ordinal()).add(kazanlushkagrobnitsa);

        Marker muzeikazanluk = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.62017791000364, 25.3979046525643))
                        .title("Литературно-художествен музей „Чудомир“")
                        .snippet("гр. Казанлък, обл. Стара Загора")
        );
        muzeikazanluk.setTag("muzeikazanluk");
        markers.get(Oblasti.StZagora.ordinal()).add(muzeikazanluk);

        Marker rozhdestvohristovo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.716321529011324, 25.329161944618644))
                        .title("Храм-паметник „Рождество Христово“")
                        .snippet("гр. Шипка, обл. Стара Загора")
        );
        rozhdestvohristovo.setTag("rozhdestvohristovo");
        markers.get(Oblasti.StZagora.ordinal()).add(rozhdestvohristovo);

        Marker pametniknasvobodata = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.74846936154786, 25.321319073194438))
                        .title("Паметник на свободата")
                        .snippet("Връх Шипка, обл. Стара Загора")
        );
        pametniknasvobodata.setTag("pametniknasvobodata");
        markers.get(Oblasti.StZagora.ordinal()).add(pametniknasvobodata);

        Marker pametniknaopulchentsite = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.750706849305224, 25.325481861327077))
                        .title("Паметник на опълченците на връх Шипка")
                        .snippet("Връх Шипка, обл. Стара Загора")
        );
        pametniknaopulchentsite.setTag("pametniknaopulchentsite");
        markers.get(Oblasti.StZagora.ordinal()).add(pametniknaopulchentsite);

        Marker shumenskakrepost = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.26270665229036, 26.89441815074883))
                        .title("Историко-археологически резерват „Шуменска крепост“")
                        .snippet("гр. Шумен, обл. Шумен")
        );
        shumenskakrepost.setTag("shumenskakrepost");
        markers.get(Oblasti.Shumen.ordinal()).add(shumenskakrepost);

        Marker muzeishumen = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.27049125026005, 26.927531381432395))
                        .title("Регионален исторически музей Шумен")
                        .snippet("гр. Шумен, обл. Шумен")
        );
        muzeishumen.setTag("muzeishumen");
        markers.get(Oblasti.Shumen.ordinal()).add(muzeishumen);

        Marker pametnikshumen = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.26195870143384, 26.92304851396284))
                        .title("Паметник „Създатели на българската държава“")
                        .snippet("гр. Шумен, обл. Шумен")
        );
        pametnikshumen.setTag("pametnikshumen");
        markers.get(Oblasti.Shumen.ordinal()).add(pametnikshumen);

        Marker dzhamiqshumen = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.269721051742884, 26.909996185127827))
                        .title("Томбул джамия „Шериф Халил паша“")
                        .snippet("гр. Шумен, обл. Шумен")
        );
        dzhamiqshumen.setTag("dzhamiqshumen");
        markers.get(Oblasti.Shumen.ordinal()).add(dzhamiqshumen);

        Marker rezervatpliska = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.388483329245425, 27.13087838328603))
                        .title("Национален историко-археологически резерват „Плиска“")
                        .snippet("гр. Плиска, обл. Шумен")
        );
        rezervatpliska.setTag("rezervatpliska");
        markers.get(Oblasti.Shumen.ordinal()).add(rezervatpliska);

        Marker golqmabazilika = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.3986797851104, 27.138605896780422))
                        .title("Голяма базилика")
                        .snippet("гр. Плиска, обл. Шумен")
        );
        golqmabazilika.setTag("golqmabazilika");
        markers.get(Oblasti.Shumen.ordinal()).add(golqmabazilika);

        Marker madarskiqkonnik = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.27797670062993, 27.119370411694657))
                        .title("Мадарския конник")
                        .snippet("с. Мадара, обл. Шумен")
        );
        madarskiqkonnik.setTag("madarskiqkonnik");
        markers.get(Oblasti.Shumen.ordinal()).add(madarskiqkonnik);

        Marker muzeivelikipreslav = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(43.145980657250476, 26.813165325603165))
                        .title("Национален историко-археологически резерват и музей „Велики Преслав“")
                        .snippet("гр. Велики Преслав, обл. Шумен")
        );
        muzeivelikipreslav.setTag("muzeivelikipreslav");
        markers.get(Oblasti.Shumen.ordinal()).add(muzeivelikipreslav);

        Marker kabile = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.529314003374274, 26.47871809899167))
                        .title("Античен град „Кабилè“")
                        .snippet("гр. Ямбол, обл. Ямбол")
        );
        kabile.setTag("kabile");
        markers.get(Oblasti.Qmbol.ordinal()).add(kabile);

        Marker muzeiqmbol = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.48364443147004, 26.50817115663561))
                        .title("Регионален исторически музей Ямбол")
                        .snippet("гр. Ямбол, обл. Ямбол")
        );
        muzeiqmbol.setTag("muzeiqmbol");
        markers.get(Oblasti.Qmbol.ordinal()).add(muzeiqmbol);

        Marker bezisten = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.48396749002138, 26.505944146454784))
                        .title("Ямболски безистен")
                        .snippet("гр. Ямбол, обл. Ямбол")
        );
        bezisten.setTag("bezisten");
        markers.get(Oblasti.Qmbol.ordinal()).add(bezisten);

        Marker muzeiboinaslava = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.48897540939774, 26.505062454405664))
                        .title("Музей на бойната слава")
                        .snippet("гр. Ямбол, обл. Ямбол")
        );
        muzeiboinaslava.setTag("muzeiboinaslava");
        markers.get(Oblasti.Qmbol.ordinal()).add(muzeiboinaslava);

        Marker muzeielhovo = mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(42.17138299950261, 26.567223725554513))
                        .title("Етнографско-археологически музей Елхово")
                        .snippet("гр. Елхово, обл. Ямбол")
        );
        muzeielhovo.setTag("muzeielhovo");
        markers.get(Oblasti.Qmbol.ordinal()).add(muzeielhovo);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.60, 25.19), 7.5F));

        Spinner dropdown=findViewById(R.id.dropdown);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.dropdown, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String)parentView.getItemAtPosition(position).toString();
                if (selectedItem.equals("Normal")) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else if (selectedItem.equals("Satellite")) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
                else if (selectedItem.equals("Terrain")) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
                else if (selectedItem.equals("Hybrid")) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        Spinner oblast = findViewById(R.id.oblast);
        ArrayAdapter<CharSequence> oblastAdapter = ArrayAdapter.createFromResource(this, R.array.oblast, android.R.layout.simple_spinner_item);
        oblastAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        oblast.setAdapter(oblastAdapter);
        oblast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String)parentView.getItemAtPosition(position).toString();
                int oblastID = 0;

                if (selectedItem.equals("Всички")) {
                    for (int i = 0; i < markers.size(); i++) {
                        for (Marker m : markers.get(i)) {
                            m.setVisible(true);
                        }
                    }
                    return;
                }

                if (selectedItem.equals("Видин")) {
                    oblastID = Oblasti.Vidin.ordinal();
                }
                else if (selectedItem.equals("Монтана")) {
                    oblastID = Oblasti.Montana.ordinal();
                }
                else if (selectedItem.equals("Враца")) {
                    oblastID = Oblasti.Vratsa.ordinal();
                }
                else if (selectedItem.equals("Плевен")) {
                    oblastID = Oblasti.Pleven.ordinal();
                }
                else if (selectedItem.equals("Велико Търново")) {
                    oblastID = Oblasti.VTurnovo.ordinal();
                }
                else if (selectedItem.equals("Русе")) {
                    oblastID = Oblasti.Ruse.ordinal();
                }
                else if (selectedItem.equals("Силистра")) {
                    oblastID = Oblasti.Silistra.ordinal();
                }
                else if (selectedItem.equals("Добрич")) {
                    oblastID = Oblasti.Dobritch.ordinal();
                }
                else if (selectedItem.equals("София Област")) {
                    oblastID = Oblasti.SofiaOblast.ordinal();
                }
                else if (selectedItem.equals("София Град")) {
                    oblastID = Oblasti.SofiaGrad.ordinal();
                }
                else if (selectedItem.equals("Ловеч")) {
                    oblastID = Oblasti.Lovech.ordinal();
                }
                else if (selectedItem.equals("Габрово")) {
                    oblastID = Oblasti.Gabrovo.ordinal();
                }
                else if (selectedItem.equals("Търговище")) {
                    oblastID = Oblasti.Turgovishte.ordinal();
                }
                else if (selectedItem.equals("Разград")) {
                    oblastID = Oblasti.Razgrad.ordinal();
                }
                else if (selectedItem.equals("Шумен")) {
                    oblastID = Oblasti.Shumen.ordinal();
                }
                else if (selectedItem.equals("Варна")) {
                    oblastID = Oblasti.Varna.ordinal();
                }
                else if (selectedItem.equals("Перник")) {
                    oblastID = Oblasti.Pernik.ordinal();
                }
                else if (selectedItem.equals("Кюстендил")) {
                    oblastID = Oblasti.Kiustendil.ordinal();
                }
                else if (selectedItem.equals("Пазарджик")) {
                    oblastID = Oblasti.Pazardzhik.ordinal();
                }
                else if (selectedItem.equals("Пловдив")) {
                    oblastID = Oblasti.Plovdiv.ordinal();
                }
                else if (selectedItem.equals("Стара Загора")) {
                    oblastID = Oblasti.StZagora.ordinal();
                }
                else if (selectedItem.equals("Сливен")) {
                    oblastID = Oblasti.Sliven.ordinal();
                }
                else if (selectedItem.equals("Ямбол")) {
                    oblastID = Oblasti.Qmbol.ordinal();
                }
                else if (selectedItem.equals("Бургас")) {
                    oblastID = Oblasti.Burgas.ordinal();
                }
                else if (selectedItem.equals("Благоевград")) {
                    oblastID = Oblasti.Blagoevgrad.ordinal();
                }
                else if (selectedItem.equals("Смолян")) {
                    oblastID = Oblasti.Smolqn.ordinal();
                }
                else if (selectedItem.equals("Кърджали")) {
                    oblastID = Oblasti.Kurdzhali.ordinal();
                }
                else if (selectedItem.equals("Хасково")) {
                    oblastID = Oblasti.Haskovo.ordinal();
                }

                for (int i = 0; i < markers.size(); i++) {
                    if (i != oblastID) {
                        for (Marker m : markers.get(i)) {
                            m.setVisible(false);
                        }
                    } else {
                        for (Marker m : markers.get(i)) {
                            m.setVisible(true);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        }
        else {
            marker.showInfoWindow();
        }

        return false;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
            }, REQUEST_CODE);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mMap.setMyLocationEnabled(true);
                currentLocation = task.getResult();
                if (currentLocation != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude() ), 10));
                }
                else {
                    mMap.setMyLocationEnabled(false);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
                break;
        }
    }
}