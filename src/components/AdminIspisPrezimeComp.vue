<template>
    <section id="couriers">
        <div class="wrapper">
            <h2 style="text-align:center" class="title">Korisnik Info</h2>
            <ul id="courier" class="slider_couriers">
                <li class="slider_courier">

                    <div class="courier_info">
                        <div>

                            <h1>{{ korisnik.name }}</h1>
                            <h1>{{ korisnik.surname }}</h1>
                            <h1>{{ korisnik.username }}</h1>

                        </div>
                        <a href="" class="btn">More Information</a>
                    </div>
                    <img src="../assets/d1.jpg" alt="img2">
                </li>
            </ul>
            <br>
            <br>
        </div>
    </section>
</template>


<script>
import axios from 'axios';
//import axios from "axios";
export default {
    name: 'AdminIspisPrezimeComp',
    data: function () {
        return {
            korisnik: {},
        }
    },
    mounted: function () {

        axios
            .get("http://localhost:8083/api/admin/korisnik/surname/" + this.$route.params.surname + `?username=${this.$store.getters.korisnik.username}`)
            .then((res) => {
                this.korisnik = res.data
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
}
</script>


<style scoped>
#couriers {
    background: #3f3d4e;
    padding: 5rem 0 25rem 0;
    text-align: center;
    position: relative;
    overflow: hidden;
}

#couriers .title {
    color: #FFFFFF;
    font-size: 3.5rem;
    margin: 0 0 .75rem 0;
    letter-spacing: 7px;
    font-weight: 600;
}

#couriers #couriers_buttons {
    list-style: none;
    padding: 0;
    margin: 2rem 0;
}

#couriers #couriers_buttons a {
    text-decoration: none;
}

#couriers #couriers_buttons li {
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

#couriers #couriers_buttons li:hover {
    background: #7ac47c;
}

#couriers #couriers_buttons .current {
    background-color: #7ac47c;
}

#couriers #courier {
    list-style: none;
}

#couriers #courier li {
    width: 300px;
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

#couriers #courier li img {
    width: 100%;
    display: block;
}



#couriers #courier li:nth-child(1) {
    left: 50%;
    margin-left: -150px;
    z-index: 2;
    bottom: 0;
}



.slider_couriers .slider_courier {
    position: relative;
}

.slider_couriers .slider_courier .courier_info {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    text-align: center;
    left: 0;
    -webkit-transform: translateX(100%);
    transform: translateX(100%);
    -webkit-transition: -webkit-transform 1s;
    transition: -webkit-transform 1s;
    transition: transform 1s;
    transition: transform 1s, -webkit-transform 1s;
    opacity: 0;
    background-color: rgba(98, 81, 81, 0.3);
}

.slider_couriers .slider_courier .courier_info .btn {
    border: 2px solid #FFFFFF;
    padding: 1em 1.5em;
    border-radius: .5em;
    text-decoration: none;
    color: #FFFFFF;
    font-weight: bold;
    text-transform: uppercase;
    font-size: .9em;
    font-weight: 700;
}

.slider_couriers .slider_courier .courier_info .btn:hover {
    border: 2px solid #7ac47c;
    color: #7ac47c;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}

.slider_couriers .slider_courier .courier_info p {
    color: black;
    font-size: 1.5em;
    font-weight: bold;
    line-height: 1;
    text-transform: uppercase;
    margin-bottom: 1em;
}

.slider_couriers .slider_courier:hover .courier_info {
    -webkit-transform: translateX(0);
    transform: translateX(0);
    opacity: 1;
}

h1 {
    color: white;
}
</style>