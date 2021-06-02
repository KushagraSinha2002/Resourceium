<template>
  <div v-if="folder" class="flex flex-col h-full">
    <!-- <page-folders-tag-setter></page-folders-tag-setter> -->
    <div class="w-10/12 mx-auto sm:w-2/3 xl:w-1/2">
      <page-folders-files-upload-icon
        :folder="folder"
        @refreshFolder="fetchFolder()"
      ></page-folders-files-upload-icon>
    </div>
    <div v-if="!$fetchState.pending" class="flex-1">
      <div v-if="documents.length > 0">
        <div class="mt-4 font-serif text-2xl text-center">
          <span class="text-4xl font-semibold text-blue-500">
            {{ documents.length }}
          </span>
          files
        </div>
        <div
          class="grid grid-cols-1 gap-4 px-2 py-5 md:grid-cols-2 sm:gap-6 bg-cream-white sm:px-5 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5 auto-rows-max"
        >
          <page-folders-files-grid-item
            v-for="file in documents"
            :key="file.id"
            :file="file"
            @refreshFolder="fetchFolder()"
          >
          </page-folders-files-grid-item>
        </div>
      </div>
      <div v-else class="flex items-center justify-center h-full">
        <common-empty-data>
          <span>Seems like you have not uploaded any files.</span>
          <span>Click on the button above to upload some.</span>
        </common-empty-data>
      </div>
    </div>
    <base-show-loading v-else></base-show-loading>
  </div>
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
  computed: {
    documents() {
      return this.folder.documents
    },
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
