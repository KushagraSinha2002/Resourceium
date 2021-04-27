<template>
  <div>Logged out successfully!</div>
</template>

<script>
export default {
  mounted() {
    window.localStorage.removeItem('username')
    window.localStorage.removeItem('userId')
  },
}
</script>
