<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <router-link to="/" class="navbar-brand">Home</router-link>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/bulk" class="nav-link">Bulk SMS</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/premium" class="nav-link">Premium SMS</router-link>
        </li>
        <li class="nav-itel">
          <router-link to="/two" class="nav-link">Two SMS</router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>


<script>
    export default {
        name: "NavBar"
    }
</script>

<style scoped>

</style>