<script>
export default {
  async mounted() {
    await this.$auth.logout()
    this.$router.push({ name: 'accounts-login' })
  },
}
</script>
