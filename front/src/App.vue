<script setup>
  import { onMounted } from 'vue'

  onMounted(() => {
    const API_URL = "http://localhost:8080/api/v1"
    fetch(API_URL)
      .then(res => console.log("안녕"))
  })
</script>

<template>
  <button>
    버튼 클릭
  </button>
</template>