<template>
    <section>
        <br>
        <h2 style="text-align:center">Lista Korisnika</h2>
        <a v-for="korisnik in korisnici" :href="`/single-korisnik/${korisnik.id}`" :key="korisnik.name" class="card">

            <div class="container">
                <h1> {{ korisnik.name }} </h1>
                <h1> {{ korisnik.surname }} </h1>
                <h1> {{ korisnik.uloga }} </h1>


                <br>
            </div>

        </a>
        <br>
        <br>
    </section>

</template>

<script>
import axios from 'axios';
export default {
    name: "KorisnikComp",
    props: ["korisnik"],
    methods: {
        seeMore: function () {
            this.$router.push("/korisnik?id=" + this.korisnik.id);
        },
        deleteKorisnik: function () {
            fetch("http://localhost:8083/api/korisnici/" + this.korisnik.id, {
                method: "DELETE",
            }).then((res) => {
                if (res.ok) {
                    window.location.reload();
                }
            });
        },
    },
    data: function () {
        return {
            korisnici: {},
        }
    },
    mounted: function () {
        axios
            .get(`http://localhost:8083/api/korisnici/ispis?username=${this.$store.getters.korisnik.username}`)
            .then((res) => {
                this.korisnici = res.data
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
