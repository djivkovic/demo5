<template>

    <h2>Pritisnite na dugme kako bi se odjavili</h2>
    <button v-on:click="submit()" class="button button_secondary">Logout</button>


</template>

<script>
import axios from "axios"
export default {
    name: "LogoutComp",
    data: function () {
        return {
            korisnik: {

            },
        };
    },
    methods: {
        submit: function () {

            axios
                .post(`http://localhost:8083/api/logout?username=${this.$store.getters.korisnik.username}`, this.korisnik)
                .then((res) => {
                    console.log(res);
                    this.$router.push("/login-korisnik");
                })
                .catch((err) => {
                    console.log(err);
                    alert("Something went wrong!");
                });

            /*fetch("http://localhost:8081/api/employees", {
              method: "POST",
              headers: {
                Accept: "application/json",
                "Content-type": "application/json",
              },
              body: JSON.stringify(this.employee),
            })
              .then((response) => response.json)
              .then((data) => {
                console.log("Success : " + data);
                this.$router.push("/employees");
              })
              .catch((err) => {
                console.log("Error : " + err);
                alert(err);
              });*/
        },
    },
};
</script>



<style scoped>
.button {
    border: none;
    outline: none;
    border-radius: 2rem;
    padding: .6rem 3.5rem;
    font-weight: 700;
    color: #FFFFFF;
    text-decoration: none;
    text-align: center;
    -webkit-transition: background .4s ease-in-out;
    transition: background .4s ease-in-out;
    margin-top: 30px;
}

.button:hover {
    background: #429645;
}

.button_secondary {
    background: rgba(98, 81, 81, 0.45);
}

.button_secondary:hover {
    background: #4d4d4d;
}
</style>