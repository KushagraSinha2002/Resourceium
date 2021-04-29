<template>
  <div>
    <div>Profile</div>
    <div>{{ user }}</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: null,
    }
  },
  mounted() {
    this.$axios
      .$get('/accounts/' + window.localStorage.getItem('userId'))
      .then((resp) => {
        this.user = resp
      })
  },
}
</script>
