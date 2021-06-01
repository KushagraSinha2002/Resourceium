<template>
  <div v-if="folder" class="flex-grow overflow-hidden">
    <page-folders-files-upload-icon
      :folder="folder"
      @refreshFolder="fetchFolder()"
    ></page-folders-files-upload-icon>
    <div
      class="grid grid-cols-1 gap-4 px-2 py-5 md:grid-cols-2 sm:gap-6 bg-cream-white sm:px-5 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5 auto-rows-max"
    >
      <page-folders-files-grid-item
        v-for="file in folder.documents"
        :key="file.id"
        :file="file"
        @refreshFolder="fetchFolder()"
      >
      </page-folders-files-grid-item>
    </div>
  </div>
  <base-show-loading v-else></base-show-loading>
</template>

<script>
export default {
  data() {
    return {
      folder: null,
    }
  },
  async fetch() {
    await this.fetchFolder()
  },
  methods: {
    async fetchFolder() {
      const folderId = this.$route.params.folderId
      const resp = await this.$axios.$get(`/folders/folder/${folderId}`)
      this.folder = resp
    },
  },
}
</script>
