<template>
  <div
    class="grid grid-cols-2 gap-4 px-2 py-5 sm:gap-6 bg-cream-white md:py-[45px] sm:px-20 md:grid-cols-2 xl:grid-cols-5 2xl:grid-cols-6 auto-rows-max"
  >
    <page-folders-create-folder-icon
      class="fixed z-20 bottom-3 right-3"
      @refreshFolders="fetchFolders()"
    ></page-folders-create-folder-icon>
    <page-folders-base-folder
      v-for="(folder, name, index) in folders"
      :key="index"
      :folder="folder"
    >
    </page-folders-base-folder>
  </div>
</template>

<script>
export default {
  middleware: ['auth'],
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
      const resp = await this.$axios.$get(`/folders/folders`)
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
