<template>
    <div>
        <h2 style="text-align: center">Porudzbine</h2>
        <div v-for="porudzbina in porudzbine" :key="porudzbina.uuid" class="card">
            <h1> {{ porudzbina.datumPorudzbine }}</h1>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: "ListPorudzbineComp",
    data: function () {
        return {
            porudzbine: {},
        }
    },
    mounted: function () {
        axios.get("http://localhost:8083/api/porudzbine")
            .then((res) => {
                this.porudzbine = res.data
            })
            .catch((err) => {
                console.log(err)
            })
    }
}
</script>
