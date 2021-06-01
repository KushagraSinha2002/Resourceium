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
    <div class="p-4 rounded-15px hover:ring-2">
      <div class="flex flex-col items-center justify-center">
        <img
          :src="require('~/assets/images/folder-icon.png')"
          :alt="`folder-${folder.id}`"
          class="w-20 h-20"
        />
        <div class="flex justify-around space-x-6">
          <div
            class="flex items-center justify-center rounded-full hover:bg-black hover:bg-opacity-20 h-7 w-7"
            @click.stop.prevent="renameFolder"
          >
            <ig-icon
              title="Rename"
              name="edit-3"
              size="sm"
              variant="primary"
            ></ig-icon>
          </div>
          <div
            class="flex items-center justify-center rounded-full hover:bg-black hover:bg-opacity-20 h-7 w-7"
            @click.stop.prevent="deleteFolder"
          >
            <ig-icon
              title="Delete"
              name="trash"
              size="sm"
              variant="danger"
            ></ig-icon>
          </div>
        </div>
      </div>
      <div
        class="p-2 overflow-hidden text-center whitespace-nowrap justify-self-end rounded-b-md font-poppins"
        style="width: 13ch"
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
    deleteFolder() {
      // send DELETE request
      console.log('delete')
    },
    renameFolder() {
      // send PUT request
      console.log('rename')
    },
    formatDate(date) {
      this.$dayjs.extend(LocalizedFormat)
      return this.$dayjs(date).format('l')
    },
  },
}
</script>
