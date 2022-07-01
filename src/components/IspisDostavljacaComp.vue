<template>
    <section>
        <br>
        <h2 style="text-align:center">Lista Dostavljaca</h2>
        <a v-for="dostavljac in dostavljaci" :key="dostavljac.name"
            class="card">

            <div class="container">
                <h1> Ime: {{ dostavljac.name }} </h1>
                <h1> Prezime:  {{ dostavljac.surname }} </h1>
                <h1>Username: {{ dostavljac.username }} </h1>


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
    name: "IspisDostavljacaComp",
    props: ["dostavljac"],
    methods: {
        seeMore: function () {
            this.$router.push("/dostavljac?id=" + this.dostavljac.id);
        },
        deleteKorisnik: function () {
            fetch("http://localhost:8083/api/korisnici/" + this.dostavljac.id, {
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
            dostavljaci: {},
        }
    },
    mounted: function () {
        axios
            .get("http://localhost:8083/api/dostavljaci/ispis")
            .then((res) => {
                this.dostavljaci = res.data
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
