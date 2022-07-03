<template>

  <div>
    <button @click="submit">Obrisi</button>
  </div>


</template>

<script>
import axios from "axios"
export default {
  name: "DeleteRestoranComp",
  data: function () {
    return {
      restoran: {

      },
    };
  },
  methods: {
    submit: function () {

      axios
        .delete(`http://localhost:8083/api/admin/brisiRestoran/` + this.$route.params.naziv + `?username=${this.$store.getters.korisnik.username}`, this.restoran)
        .then((res) => {
          console.log(res);
          this.$router.push("/restorani-ispis");
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



