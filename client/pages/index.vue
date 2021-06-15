<template>
  <div class="flex flex-col flex-grow w-full">
    <page-index-header></page-index-header>
    <div
      class="flex flex-col flex-grow w-full px-4 mx-auto duration-300 sm:w-10/12 2xl:w-9/12 transition-spacing"
    >
      <page-index-content-container></page-index-content-container>
      <page-index-loader
        :loaded="pinged"
        class="mt-7 sm:my-10"
      ></page-index-loader>
      <page-index-separator></page-index-separator>
      <page-index-footer></page-index-footer>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pinged: false,
    }
  },
  async mounted() {
    await this.$axios.$get('/')
    this.pinged = true
  },
}
</script>
