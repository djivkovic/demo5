<template>
    <section>
        <br>
        <h2 style="text-align:center">Lista Artikala</h2>
        <div class="container" v-for="artikal in artikli" :key="artikal.naziv">
            <h1> Naziv: {{ artikal.naziv }} </h1>
            <h1> Cena: {{ artikal.cena }} </h1>
            <h1> Tip: {{ artikal.tip }} </h1>
            <h1> Kolicina: {{ artikal.kolicina }} </h1>
            <h1> Opis: {{ artikal.opis }} </h1>


            <br>
            <br>
            <br>
            <br>
        </div>

        <br>
        <br>
    </section>

</template>

<script>
import axios from 'axios';
export default {
    name: "IspisiArtikleComp",
    props: ["artikal"],
    data: function () {
        return {
            artikli: {},
        }
    },
    mounted: function () {
        axios
            .get("http://localhost:8083/api/artikli/ispis")
            .then((res) => {
                this.artikli = res.data
            })
            .catch((err) => {
                console.log(err)
            })
        /*
                fetch('http://localhost:8083/api/korisnici/ispis',
                    {
                        credentials: 'include'
                    })
                    .then(response => response.json())
                    .then(data => {
                        console.log("Success:", data);
                        this.korisnik = data
                    })
                    .catch((error) => {
                        console.error("Error:", error)
                    });
            }
        */

    }
};
</script>


<style scoped>
section {
    background-color: antiquewhite;
}

a {
    text-decoration: none;
    text-align: center;
}

.container {
    border: 1px solid grey;
}
</style>
