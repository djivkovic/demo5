package vezbe.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vezbe.demo.entity.*;
import vezbe.demo.repository.*;


import java.util.*;



@Configuration
public class DatabaseConfiguration {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ArtikalRepository artikalRepository;

    @Autowired
    private DostavljacRepository dostavljacRepository;

    @Autowired
    private KomentarRepository komentarRepository;

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private TipKupcaRepository tipKupcaRepository;

    @Autowired
    private PorudzbineArtikliRepository porudzbineArtikliRepository;








    @Bean
    public boolean instantiate() {
        Calendar c = new GregorianCalendar();

        Korisnik djole = new Korisnik("djole123", "djole123", "Djordje", "Ivkovic", Pol.musko, c.getTime(), Uloga.ADMIN);
        Korisnik dule = new Korisnik("dule123", "dule123", "Dusan", "Stojanovic", Pol.musko, c.getTime(), Uloga.ADMIN);

        korisnikRepository.saveAll(List.of(djole, dule));


        Menadzer pavle = new Menadzer("pavle123", "pavle123", "Nikola", "Pantic");

        menadzerRepository.save(pavle);

        Kupac kupac = new Kupac("zika123", "zika123", "Zivan", "Zivanovic");
        kupac.setUloga(Uloga.KUPAC);
        kupacRepository.save(kupac);

        Lokacija lokacija1 = new Lokacija("14N 23S 17W 22E", "15N 25S 57W 25E", "lokacija123");
        lokacija1.setID(1L);
        Lokacija lokacija2 = new Lokacija("14N 23S 27W 22E", "15N 25S 27W 25E", "lokacija12222");
        lokacija2.setID(2L);
        lokacijaRepository.saveAll(List.of(lokacija1, lokacija2));

        Restoran restoran1 = new Restoran("CIAO", "Italijanski", new Lokacija());
        Restoran restoran2 = new Restoran("Lougi", "Francuski", new Lokacija());
        restoran1.setLokacija(lokacija1);
        restoran2.setLokacija(lokacija2);
        restoranRepository.saveAll(List.of(restoran1, restoran2));

        Artikal pizza = new Artikal("Pizza", 14.43,Tip.jelo, 2, "Italian food");
        Artikal pepsi = new Artikal("Pepsi", 14.43,Tip.pice, 2, "Drinks");
        pizza.setRestaurant(restoran1);
        pepsi.setRestaurant(restoran1);
        pizza.setRestaurant(restoran2);
        artikalRepository.saveAll(List.of(pizza, pepsi));



        Porudzbina porudzbina = new Porudzbina(restoran1, kupac);
        Porudzbina porudzbina2 = new Porudzbina(restoran2, kupac);
        PorudzbineArtikli pa = new PorudzbineArtikli(porudzbina, pizza, 50, 50* pizza.getCena());
        PorudzbineArtikli pa2 = new PorudzbineArtikli(porudzbina2, pizza, 20, 20* pizza.getCena());
        porudzbineArtikliRepository.save(pa);
        porudzbineArtikliRepository.save(pa2);
        porudzbina.setArtikli(Set.of(pa, pa2));
        porudzbina.setStatus(Status.ceka_dostavljaca);
        porudzbinaRepository.save(porudzbina);
        porudzbinaRepository.save(porudzbina2);
        kupac.setPorudzbine(Set.of(porudzbina, porudzbina2));
        kupacRepository.save(kupac);




        Dostavljac dostavljac = new Dostavljac("marko123", "marko123", "Marko", "Markovic");
        dostavljac.setPorudzbine(Set.of(porudzbina));
        dostavljacRepository.save(dostavljac);


        restoran1.setPorudzbine(Set.of(porudzbina));
        restoran1.setArtikli(Set.of(pizza,pepsi));
        restoran1.setLokacija(lokacija1);
        restoranRepository.saveAndFlush(restoran1);
        return true;
    }
}