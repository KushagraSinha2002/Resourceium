<template>
  <div class="relative flex flex-col w-full h-full py-3">
    <page-folders-create-folder-icon
      @refreshFolders="fetchFolders()"
    ></page-folders-create-folder-icon>
    <page-folders-search-folder-icon
      @searchFolders="searchFolders"
    ></page-folders-search-folder-icon>
    <page-folders-tabs
      v-model="selectedTab"
      :possible="possibleTabs"
    ></page-folders-tabs>
    <div v-if="!loading" class="w-full h-full mt-2">
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
      possibleTabs: ['All', 'Favorites', 'Search'],
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
      if (this.selectedTab === 'Favorites') {
        await this.fetchFavorites()
      } else if (this.selectedTab === 'All') {
        await this.fetchFolders()
      }
    },
  },
  methods: {
    transformFolders(folders) {
      folders.map((element) => {
        element.dateOfUpload = this.$dayjs(element.dateOfUpload)
        element.lastEdited = this.$dayjs(element.lastEdited)
        return element
      })
      return folders
    },
    async fetchFolders() {
      this.selectedTab = 'All'
      this.loading = true
      const resp = await this.$axios.$get('/folders/user-folders')
      this.folders = this.transformFolders(resp)
      this.loading = false
    },
    async searchFolders(tags) {
      this.selectedTab = 'Search'
      this.loading = true
      const resp = await this.$axios.$get('/folders/by-tags', {
        params: { tags },
      })
      this.folders = this.transformFolders(resp)
      this.loading = false
    },
    async fetchFavorites() {
      this.selectedTab = 'Favorites'
      this.loading = true
      const resp = await this.$axios.$get('/folders/user-favorite-folders')
      this.folders = this.transformFolders(resp)
      this.loading = false
    },
  },
}
</script>
