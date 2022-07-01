<template>
    <section id="restaurants">
        <div class="wrapper">
            <h2 class="title">Our Restaurants</h2>

            <h2 style="text-align:center" class="title" v-for="r in restoran" :key="r.id"> Ime
                restorana: {{ r.naziv }} -- Tip restorana: {{ r.tip_restorana }}
            </h2>


            <ul id="slider" class="slider_restaurants">

                <li>
                    <img src="../assets/image2.jpg" alt="img2">

                </li>

            </ul>
        </div>
    </section>

</template>
<script>
import axios from 'axios';
//import axios from "axios";
export default {
    props: ["restorani"],
    name: 'RestoraniIspisComp',
    data: function () {
        return {
            restoran: {
            },
        }
    },
    computed:
    {
    restoranNaziv()
    {
            return this.restoran.naziv;
    }
    },
   
    methods: {
        fetchRestaurant() {
            axios
                .get("http://localhost:8083/api/restorani/ispis")
                .then((res) => {
                    this.restoran = res.data;
                })
                .catch((err) => {
                    console.log(err)
                })
        }
    },
    mounted: function () {
        this.fetchRestaurant()
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
}
</script>

<style scoped>
#restaurants {
    background: #3f3d4e;
    padding: 5rem 0 25rem 0;
    text-align: center;
    position: relative;
    overflow: hidden;
}

#restaurants .title {
    color: #FFFFFF;
    font-size: 3.5rem;
    margin: 0 0 .75rem 0;
    letter-spacing: 7px;
    font-weight: 600;
}

#restaurants #slider_buttons {
    list-style: none;
    padding: 0;
    margin: 2rem 0;
}

#restaurants #slider_buttons a {
    text-decoration: none;
}

#restaurants #slider_buttons li {
    width: 9px;
    height: 9px;
    margin: 0 .5rem;
    position: relative;
    border-radius: 100%;
    display: inline-block;
    background-color: #5b596e;
    -webkit-transition: background .4s ease-in-out;
    transition: background .4s ease-in-out;
}

#restaurants #slider_buttons li:hover {
    background: #7ac47c;
}

#restaurants #slider_buttons .current {
    background-color: #7ac47c;
}

#restaurants #slider {
    list-style: none;
}

#restaurants #slider li {
    width: 600px;
    height: 375px;
    overflow: hidden;
    background-color: #FFFFFF;
    box-shadow: 0px 0px 57px 0px rgba(4, 6, 9, 0.25);
    -webkit-box-shadow: 0px 0px 57px 0px rgba(4, 6, 9, 0.25);
    -moz-box-shadow: 0px 0px 57px 0px rgba(4, 6, 9, 0.25);
    position: absolute;
    z-index: 3;
    border-radius: 2rem 2rem 0 0;
    -webkit-transition: opacity 0.1s 0.3s,
        -webkit-transform 0.4s;
    transition: opacity 0.1s 0.3s,
        -webkit-transform 0.4s;
    transition: transform 0.4s,
        opacity 0.1s 0.3s;
    transition: transform 0.4s,
        opacity 0.1s 0.3s,
        -webkit-transform 0.4s;
}

#restaurants #slider li img {
    width: 100%;
    display: block;
}



#restaurants #slider li:nth-child(1) {
    left: 50%;
    margin-left: -300px;
    z-index: 4;
    bottom: 0;
}



.slider_restaurants :hover {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
    -webkit-transition: all 0.6s ease-in-out;
    transition: all 0.6s ease-in-out;
}
</style>
