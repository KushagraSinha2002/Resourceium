<template>
  <div class="p-5">
    <div
      v-if="stats && app"
      class="grid grid-cols-1 gap-6 md:grid-cols-2 xl:col-span-4 sm:gap-10 sm:mt-4"
    >
      <page-dashboard-stat-item
        label="Folders created"
        icon="folders-used.png"
        :value="stats.folders"
        :limit="app.folders"
      >
      </page-dashboard-stat-item>
      <page-dashboard-stat-item
        label="Disk Usage"
        icon="disk-usage.png"
        :value="getDiskUsage"
        :limit="getTotalDiskUsage"
      >
      </page-dashboard-stat-item>
      <page-dashboard-stat-item
        label="Discussions joined"
        icon="discussions-used.png"
        :value="stats.discussions"
        :limit="app.discussions"
      >
      </page-dashboard-stat-item>
      <page-dashboard-stat-item
        label="Files created"
        icon="files-used.png"
        :value="stats.files"
        :limit="app.files"
      >
      </page-dashboard-stat-item>
    </div>
    <div v-else>
      <base-show-loading></base-show-loading>
    </div>
  </div>
</template>

<script>
import fileSize from 'filesize'

const size = fileSize.partial({ spacer: '' })

export default {
  data() {
    return {
      stats: null,
      app: null,
    }
  },
  async fetch() {
    this.stats = await this.$axios.$get('/core/stats/dashboard')
    this.app = await this.$axios.$get('/core/stats/app')
  },
  computed: {
    getDiskUsage() {
      return size(this.stats.size)
    },
    getTotalDiskUsage() {
      return size(this.app.size)
    },
  },
}
</script>
