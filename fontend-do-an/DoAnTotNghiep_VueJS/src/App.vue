<script setup>
// import HelloWorld from './components/HelloWorld.vue'
// import TheWelcome from './components/TheWelcome.vue'
// import _imports_0 from '/DoAnTotNghiep_VueJS/images/448146698_954136146506599_6855434188932882489_n.jpg'
let fullname = "Nguyen Van A"
let studentCode = "20210501"
let info = {

  key: "abc",
  fullname: "nguyen van a",
  car: "bmw x7"
}

</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Navbar</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Dropdown
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">Action</a></li>
              <li><a class="dropdown-item" href="#">Another action</a></li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
          </li>
          <li class="nav-item">
            <a class="nav-link disabled" aria-disabled="true">Disabled</a>
          </li>
        </ul>
        <form class="d-flex" role="search">
          <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
      </div>
    </div>
  </nav>
  <div class="alert alert-primary" role="alert">
    {{ info.key }}<br>
    {{ info.fullname }}<br>
    {{ info.car }}
  </div>

  <div class="card" style="width: 18rem;">
    <img src="/public/448146698_954136146506599_6855434188932882489_n.jpg" class="card-img-top" alt="...">
    <div class="card-body">
      <p class="card-text">{{ info.fullname }}</p>
      <h5 class="card-title">{{ info.key }}</h5>
      <a href="#" class="btn btn-primary"> {{ info.car }}</a>
    </div>

  </div>


</template>

<style scoped></style>
