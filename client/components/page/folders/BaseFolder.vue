<template>
  <NuxtLink
    :id="`folder-${folder.id}`"
    v-tippy="{ placement: 'top', arrow: true, animation: 'shift-toward' }"
    :to="{
      name: 'folders-folderId',
      params: { folderId: folder.id },
    }"
    :content="getFolderDetails"
    class="flex flex-col items-center"
  >
    <div class="rounded-15px hover:ring-2">
      <div class="flex justify-center md:p-4">
        <img
          :src="require('~/assets/images/folder-icon.png')"
          :alt="`folder-${folder.id}`"
        />
        <div
          class="z-20 flex items-center justify-center w-8 h-8 mt-auto mb-2 -ml-2 transition duration-500 transform rounded-full cursor-pointer hover:bg-black hover:bg-opacity-40"
          @click.stop.prevent="handleMenuOpen"
        >
          <img :src="require('~/assets/images/open-menu.png')" class="h-6" />
        </div>
      </div>
      <div
        class="p-2 text-center line-clamp-1 justify-self-end rounded-b-md font-poppins"
      >
        {{ folder.title }}
      </div>
    </div>
  </NuxtLink>
</template>

<script>
import LocalizedFormat from 'dayjs/plugin/localizedFormat'

export default {
  props: {
    folder: {
      required: true,
      type: Object,
    },
  },
  data() {
    return {
      disabled: false,
    }
  },
  computed: {
    getFolderDetails() {
      const folder = this.folder
      let template = '<ol style="text-align: left">'
      template += `
        <li>Files: ${folder.documents.length}</li>
        <li>Uploaded: ${this.formatDate(folder.dateOfUpload)}</li>
        <li>Updated: ${this.formatDate(folder.lastEdited)}</li>
      `
      if (folder.tags.length > 0) {
        template += `
          <li>${folder.tags.join(', ')}</li>
        `
      }
      template += '</ol>'
      return template
    },
  },
  methods: {
    handleMenuOpen() {
      // event.stopPropagation()
      console.log('here')
    },
    formatDate(date) {
      this.$dayjs.extend(LocalizedFormat)
      return this.$dayjs(date).format('l')
    },
  },
}
</script>
