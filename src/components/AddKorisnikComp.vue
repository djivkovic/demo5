<template>
    <div class="container">
            <label for="name">First Name</label>
            <input type="text" id="fname" name="name" placeholder="Your name.." v-model="korisnik.name">

            <label for="surname">Last Name</label>
            <input type="text" id="lname" name="surname" placeholder="Your last name.." v-model="korisnik.surname">

            <label for="username" name="username">Username</label>
            <input type="text" id="sname" name="surname" placeholder="Your username.." v-model="korisnik.username">

            <label for="subject">Password</label>
            <input type="password" name="password" id="password" v-model="korisnik.password" placeholder="Your password...">
            <button v-on:click="submit()">Submit</button>
    </div>

</template>

<script>

import axios from "axios"
export default {
    name: "AddKorisnikView",
    data: function () {
        return {
            korisnik: {
                name: "",
                surname: "",
            },
        };
    },
    methods: {
        submit: function () {

            axios
                .post("http://localhost:8083/api/register", this.korisnik)
                .then((res) => {
                    console.log(res);
                    this.$router.push("/korisnik");
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


input[type=text],
input[type=password],
select {
    width: 100%;
    /* Full width */
    padding: 12px;
    /* Some padding */
    border: 1px solid #ccc;
    /* Gray border */
    border-radius: 4px;
    /* Rounded borders */
    box-sizing: border-box;
    /* Make sure that padding and width stays in place */
    margin-top: 6px;
    /* Add a top margin */
    margin-bottom: 16px;
    /* Bottom margin */
    resize: vertical
        /* Allow the user to vertically resize the textarea (not horizontally) */
}

/* Style the submit button with a specific background color etc */
input[type=submit] {
    background-color: #04AA6D;
    color: white;
    padding: 12px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

/* When moving the mouse over the submit button, add a darker green color */
input[type=submit]:hover {
    background-color: #45a049;
}

/* Add a background color and some padding around the form */
.container {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}
</style>
