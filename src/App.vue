<template>

  <Header />




  <router-view />
</template>

<script>
import Header from './components/layout/Header';



export default
  {
    name: "AppVue",
    components:
    {
      Header
    }
  }
</script>







<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

* {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

body {
  font-family: Georgia,
    'Times New Roman',
    Times,
    serif;
}
</style>
