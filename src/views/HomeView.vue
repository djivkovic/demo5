<template>
  <section id="app">
    <section id="banner">
      <div class="wrapper">
        <header>
          <a href="#" id="logo">C<span> </span>O <span> </span>S <span> </span>M <span> </span>O <span>
            </span>S</a>


          <div class="navigation">
            <nav>
              <a href="http://localhost:8082/" id="nav_home">Home</a>
              <a href="#" id="nav_restaurants">Restaurants</a>
              <a href="../login/login_form.html" id="nav_login" target="_blank"> Login</a>
              <a href="#" id="nav_registration">Registration</a>
            </nav>
          </div>
        </header>

        <div class="content">
          <h1 class="title"><span>COSMOS</span> <span>F</span>ood <span>D</span>elivery</h1>

          <h2 class="sub_title">FASTEST DELIVERY IN SPACE!</h2>

          <div class="button_wrapper">
            <a href="#" class="button button_highlight">Learn More</a>
            <a href="http://localhost:8082/about" class="button button_secondary">About us</a>
          </div>
        </div>
      </div>
    </section>
  </section>
</template>

<script>





export default {
  data() {
    return {
    
    }
  },

  methods:
  {
  

  }
}
</script>

<style scoped>
.wrapper {
  width: 1440px;
  height: 870px;
  max-width: 100%;
  margin: 0 auto;
}

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
}

.button:hover {
  background: #429645;
}

.button_highlight {
  background: #7ac47c;
}

.button_secondary {
  background: rgba(98, 81, 81, 0.45);
}

.button_secondary:hover {
  background: #4d4d4d;
}

.button_small {
  padding: .3rem 1.5rem;
}

.button_large {
  padding: 1rem 5rem;
  font-size: 1.35rem;
}

.cf:after,
.cf:before {
  content: " ";
  display: table;
}

.cf:after {
  clear: both;
}

#banner {
  background-image: url(../assets/baner_background.jpg);
  background-repeat: no-repeat;
  background-size: cover;
}

#banner header {
  padding: 2rem 0;
}

#banner header #logo {
  color: white;
  font-weight: 900;
  font-size: 1.95rem;
  float: left;
  margin: 0;
  text-decoration: none;
}

#banner header #logo span {
  font-weight: 400;
}

#banner header nav {
  float: right;
}

#banner header nav a {
  color: #FFFFFF;
  font-weight: 700;
  font-size: 1.3em;
  text-decoration: none;
  margin-left: 2rem;
}

#banner .content {
  padding: 4rem 0 8rem 0;
}

#banner .content .title,
#banner .content .sub_title {
  color: #FFFFFF;
  text-align: center;
  word-spacing: 0.5em;
}

#banner .content .title #bold,
#banner .content .sub_title #bold {
  font-weight: 600;
}

#banner .content .title {
  font-size: 3.5rem;
  margin: 0;
}

#banner .content .sub_title {
  font-weight: 700;
  font-size: 2rem;
  color: #FFFFFF;
  width: 600px;
  margin: 1.5rem auto 2rem auto;
  line-height: 2;
}

#banner .content .button_wrapper {
  text-align: center;
  margin: 2rem 0;
}

#banner .content .button_wrapper .button {
  margin: 0 .75rem;
}
</style>
