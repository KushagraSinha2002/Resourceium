<template>
  <div class="relative flex flex-col w-full h-full py-3">
    <page-folders-create-folder-icon
      @refreshFolders="fetchFolders()"
    ></page-folders-create-folder-icon>
    <page-folders-tabs
      v-model="selectedTab"
      :possible="possibleTabs"
    ></page-folders-tabs>
    <div v-if="!loading" class="w-full mt-2">
      <div
        v-if="folders.length > 0"
        class="grid grid-cols-2 gap-4 px-2 py-5 sm:gap-6 bg-cream-white sm:px-5 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-6 2xl:grid-cols-7 auto-rows-max"
      >
        <page-folders-base-folder
          v-for="(folder, name, index) in folders"
          :key="index"
          :folder="folder"
          @refreshFolders="fetchFolders()"
        >
        </page-folders-base-folder>
      </div>
      <div v-else class="flex items-center justify-center h-full">
        <common-empty-data>
          <span>Seems like you have not created any folders.</span>
          <span>Click on the button on the bottom right to create one.</span>
        </common-empty-data>
      </div>
    </div>
    <div v-else class="flex items-center justify-center w-full h-full">
      <base-show-loading></base-show-loading>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedTab: 'All',
      possibleTabs: ['All', 'Favorites'],
      folders: [],
      loading: false,
    }
  },
  async fetch() {
    this.loading = true
    await this.fetchFolders()
    this.loading = false
  },
  watch: {
    async selectedTab() {
      this.loading = true
      if (this.selectedTab === 'Favorites') {
        await this.fetchFavorites()
      } else if (this.selectedTab === 'All') {
        await this.fetchFolders()
      }
      this.loading = false
    },
  },
  methods: {
    async fetchFolders() {
      const resp = await this.$axios.$get('/folders/user-folders')
      resp.map((element) => {
        element.dateOfUpload = this.$dayjs(element.dateOfUpload)
        element.lastEdited = this.$dayjs(element.lastEdited)
        return element
      })
      this.folders = resp
      this.selectedTab = 'All'
    },
    async fetchFavorites() {
      const resp = await this.$axios.$get('/folders/user-favorite-folders')
      resp.map((element) => {
        element.dateOfUpload = this.$dayjs(element.dateOfUpload)
        element.lastEdited = this.$dayjs(element.lastEdited)
        return element
      })
      this.folders = resp
    },
  },
}
</script>
