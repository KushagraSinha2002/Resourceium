<template>
  <div
    class="flex items-center justify-between w-10/12 py-4 mx-auto sm:w-2/3 xl:w-1/2"
  >
    <div class="flex flex-col items-center md:flex-row group">
      <img
        :src="require('~/assets/images/favorite-folder.png')"
        alt="favorite icon"
        class="cursor-pointer h-9 md:h-12"
        @click="toggleFavorite"
      />
      <div
        class="ml-1 text-2xl font-semibold text-center duration-300 font-poppins group-hover:ml-2 transition-spacing"
      >
        <span>{{ favorites }}</span>
        <span class="text-sm text-true-gray-600">favorite(s)</span>
      </div>
    </div>
    <div class="flex flex-col items-center md:flex-row-reverse group">
      <img
        :src="require('~/assets/images/share-folder.png')"
        alt="favorite icon"
        class="cursor-pointer h-9 md:h-12"
      />
      <div
        class="mr-1 text-2xl font-semibold text-center duration-300 font-poppins group-hover:mr-2 transition-spacing"
      >
        <span>{{ shares }}</span>
        <span class="text-sm text-true-gray-600">share(s)</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    folderId: { type: Number, required: true },
  },
  data() {
    return {
      favorites: 0,
      shares: 0,
    }
  },
  async fetch() {
    this.favorites = await this.$axios.$get(`/favorite/${this.folderId}`)
  },
  methods: {
    async toggleFavorite() {
      await this.$axios.$post(`/favorite/toggle/${this.folderId}`)
      await this.$fetch()
    },
  },
}
</script>
