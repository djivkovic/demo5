package vezbe.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

    @Entity
    public class PorudzbineArtikli implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(cascade = CascadeType.ALL)
        @JsonIgnore
        private Porudzbina porudzbina;

        @ManyToOne
        private Artikal artikal;

        private int kolicina;

        private Double ukupnaCena;

        public PorudzbineArtikli() { super(); }

        public PorudzbineArtikli(Porudzbina porudzbina, Artikal artikal, int kolicina, Double ukupnaCena) {
            this.porudzbina = porudzbina;
            this.artikal = artikal;
            this.kolicina = kolicina;
            this.ukupnaCena = ukupnaCena;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Porudzbina getPorudzbina() {
            return porudzbina;
        }

        public void setPorudzbina(Porudzbina porudzbina) {
            this.porudzbina = porudzbina;
        }

        public Artikal getArtikal() {
            return artikal;
        }

        public void setArtikal(Artikal artikal) {
            this.artikal = artikal;
        }

        public int getKolicina() {
            return kolicina;
        }

        public void setKolicina(int kolicina) {
            this.kolicina = kolicina;
        }

        public Double getUkupnaCena() {
            return ukupnaCena;
        }

        public void setUkupnaCena(int kolicina, Double cena) {
            ukupnaCena = kolicina*cena;
        }

        @Override
        public String toString() {
            return "PorudzbineArtikli{" +
                    "id=" + id +
                    ", porudzbina=" + porudzbina +
                    ", artikal=" + artikal +
                    ", kolicina=" + kolicina +
                    ", ukupnaCena=" + ukupnaCena +
                    '}';
        }
}
