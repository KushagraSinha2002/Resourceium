<template>
  <div
    class="grid grid-cols-2 gap-4 px-2 py-5 sm:gap-6 bg-cream-white md:py-10 sm:px-10 md:grid-cols-2 xl:grid-cols-5 2xl:grid-cols-6 auto-rows-max"
  >
    <page-folders-create-folder-icon
      class="fixed z-20 bottom-3 right-3"
      @refreshFolders="fetchFolders()"
    ></page-folders-create-folder-icon>
    <div
      v-for="(folder, name, index) in folders"
      :key="index"
      class="relative flex flex-col items-center shadow-lg"
    >
      <FontAwesomeIcon
        :icon="['fas', 'ellipsis-v']"
        class="absolute h-4 text-black transition duration-500 transform bg-transparent cursor-pointer hover:text-gray-800 md:h-6 right-3 md:top-4 top-3 md:right-4 hover:scale-110"
      ></FontAwesomeIcon>
      <div class="flex flex-none w-full p-2 bg-gray-200 md:p-4 rounded-t-15px">
        <ig-icon
          no-size
          no-color
          name="folder"
          class="h-16 mx-auto sm:h-24 text-amber-500"
        ></ig-icon>
      </div>
      <div
        class="flex-grow w-full p-2 text-center rounded-b-md bg-denim-300 line-clamp-2 font-poppins"
      >
        {{ folder.title }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      folders: [],
    }
  },
  mounted() {
    this.fetchFolders()
  },
  methods: {
    async fetchFolders() {
      const resp = await this.$axios.$get(
        `/folders/${this.$route.params.userId}`
      )
      resp.map((element) => {
        element.dateOfUpload = this.$moment(element.dateOfUpload)
        element.lastEdited = this.$moment(element.lastEdited)
        return element
      })
      this.folders = resp
    },
  },
}
</script>
