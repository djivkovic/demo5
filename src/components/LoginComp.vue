<template>

        <div class="container">
        <div class="header">
            <h1>Login</h1>
        </div>
        <div class="main">

            <span>
                <label for="username">Username:</label>
                <input type="text" placeholder="Username" name="username"  v-model="korisnik.username">
            </span><br>
            <span>

                <label for="password">Password:</label>
                <input type="password" placeholder="Password" name="password" v-model="korisnik.password">
            </span><br>
        <button v-on:click="submit()">Login</button>



        </div>
    </div>


</template>


<script>
import axios from "axios"
export default {
    name: "LoginView",
    data: function () {
        return {
            korisnik: {
                username: "",
                password: "",
            },
            
        };
    },
    methods: {
        submit: function () {
            axios
                .post("http://localhost:8083/api/login", this.korisnik)
                .then((res) => {
                    console.log(res);
                    console.log("Uspesno prijavljen");
                    console.log(this.korisnik)
                    this.$store.commit('addKorisnik', this.korisnik)

                    this.korisnik.username = '';
                    this.korisnik.password = '';
               
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

body {
    font-family: sans-serif;
    background-image: url(../assets/background.jpg);
    background-repeat: no-repeat;
    overflow: hidden;
    background-size: cover;
}

.container {
    width: 380px;
    margin: 7% auto;
    border-radius: 25px;
    background-color: rgba(126, 196, 94, 0.4);
    box-shadow: 0 0 17px #333;
}

.header {
    text-align: center;
    padding-top: 75px;
}

.header h1 {
    color: black;
    font-size: 45px;
    margin-bottom: 80px;
}

.main {
    text-align: center;
}

.main input,
button {
    width: 300px;
    height: 40px;
    border: none;
    outline: none;
    padding-left: 40px;
    box-sizing: border-box;
    font-size: 15px;
    color: #333;
    margin-bottom: 40px;
}

.main input[type="submit"] {
    padding-left: 0;
    background-color: #83acf1;
    letter-spacing: 1px;
    font-weight: bold;
    margin-bottom: 70px;
}

.main input[type="submit"]:hover {
    box-shadow: 2px 2px 5px #555;
    background-color: #7799d4;
}

.main input:hover {
    box-shadow: 2px 2px 5px #555;
    background-color: #ddd;
}

.main span {
    position: relative;
}

.main i {
    position: absolute;
    left: 15px;
    color: #333;
    font-size: 16px;
    top: 2px;
}
</style>