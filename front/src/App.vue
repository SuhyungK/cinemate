<script setup>
import { onMounted, ref } from "vue";

let count = ref(0);
let title = "Hello Vue";
var movieList = ref([]);
const imgUrl = "https://image.tmdb.org/t/p/w500";

onMounted(() => {
  const API_URL = "http://localhost:8080/api/v1/movies";
  fetch(API_URL)
    .then((res) => res.json())
    .then((data) => {
      movieList.value.push(data[0]);
      movieList.value.push(data[1]);
      movieList.value.push(data[2]);
      console.log(data);
    });
});
</script>

<template></template>

<style scoped></style>
