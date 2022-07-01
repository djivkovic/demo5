<template>
    <header class="header">
        <h1>Cosmos</h1>
        <nav>
            <router-link to="/">Home</router-link> |
            <router-link to="/about">About</router-link>|
            <router-link to="/korisnik">Korisnici</router-link>|
            <router-link to="/add-korisnik">ADD Korisnik</router-link>|
            <router-link to="/login-korisnik">Login Korisnik</router-link>|
            <router-link to="/list-poruzbine">Porudzbine</router-link>|
            <router-link to="/logout-korisnik">Logout Korisnik</router-link>|
            <router-link to="/restoran-ispis/1">Restoran CIAO</router-link>|
            <router-link to="/restoran-ispis/2">Restoran Louigi</router-link>|
            <router-link to="/korpa">Korpa</router-link>|
            <router-link to="/artikli-ispis">Artikli</router-link>|
            <router-link to="/restorani-ispis">Restorani</router-link>|
            <router-link to="/create-restoran">Kreiraj restoran</router-link>|
            <router-link to="/create-artikal">Kreiraj artikal</router-link>|
            <router-link to="/ispis-dostavljaca">Ispis Dostavljaca</router-link>|
            <router-link to="/create-dostavljaca">Kreiraj Dostavljaca</router-link>|
            <router-link to="/ispisi-menadzere">Ispis Menadzera</router-link>|
            <router-link to="/create-menadzer">Kreiraj Menadzera</router-link>|
            <router-link to="/admin-ispis-ime/Djordje">Admin Ispis Ime</router-link>|
            <router-link to="/admin-ispis-prezime/Stojanovic">Admin Ispis Prezime</router-link>|
            <router-link to="/delete-restoran/CIAO">Obrisi restoran CIAO</router-link>|






























        </nav>
    </header>


</template>

<script>
export default
    {
        name: "HeadeR"
    }

</script>

<style scoped>
.header {
    background: #333;
    color: #fff;
    text-align: center;
    padding: 10px;
}

.header a {
    color: #fff;
    padding-right: 5px;
    text-decoration: none;
}
</style>