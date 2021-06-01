<template>
  <div v-if="folder" class="flex-grow overflow-hidden">
    <page-folders-files-upload-icon
      :folder="folder"
      @refreshFolder="fetchFolder()"
    ></page-folders-files-upload-icon>
    <page-folders-files-table
      :files="folder.documents"
    ></page-folders-files-table>
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
