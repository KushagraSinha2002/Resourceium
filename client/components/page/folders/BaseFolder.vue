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
            title="Rename Folder"
            @click.stop.prevent="renameFolder"
          >
            <Edit3Icon
              title="Rename"
              class="text-blue-400"
              size="20"
            ></Edit3Icon>
          </div>
          <div
            class="flex items-center justify-center rounded-full hover:bg-black hover:bg-opacity-20 h-7 w-7"
            title="Delete Folder"
            @click.stop.prevent="deleteFolder"
          >
            <TrashIcon
              title="Delete"
              class="text-red-500"
              size="20"
            ></TrashIcon>
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
import { Edit3Icon, TrashIcon } from 'vue-feather-icons'
import LocalizedFormat from 'dayjs/plugin/localizedFormat'
import { cleanDoubleSlashes } from 'ufo'
import { sleep } from '~/utils/sleep.js'

export default {
  components: { Edit3Icon, TrashIcon },
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
        const names = folder.tags.map((tag) => tag.name)
        template += `
          <li>Tags: ${names.join(', ')}</li>
        `
      }
      template += '</ol>'
      return template
    },
  },
  methods: {
    async deleteFolder() {
      // send DELETE request
      if (
        confirm(
          `All files inside will also be deleted. This action can not be reversed. Delete folder '${this.folder.title}'?`
        )
      ) {
        // delete all files from storage server
        for (const document of this.folder.documents) {
          const deleteURL = cleanDoubleSlashes(
            this.$config.storageServer + document.deleteUrl
          )
          await this.$axios.$delete(deleteURL)
        }
        await this.$axios
          .$delete(`/folders/delete/${this.folder.id}`)
          .then(async () => {
            await sleep(20)
            this.$emit('refreshFolders')
          })
          .catch(() => {
            this.$addAlert({
              message:
                'There was some problem processing your request. Please try again later.',
              type: 'danger',
            })
          })
      }
    },
    async renameFolder() {
      // send PATCH request
      const title = prompt('Enter new name', this.folder.title)
      if (title) {
        await this.$axios
          .$patch(`/folders/rename/${this.folder.id}`, { title })
          .then(async () => {
            await sleep(20)
            this.$emit('refreshFolders')
          })
          .catch((err) => {
            this.$addAlert({
              message: err.response.data,
              type: 'danger',
            })
          })
      }
    },
    formatDate(date) {
      this.$dayjs.extend(LocalizedFormat)
      return this.$dayjs(date).format('l')
    },
  },
}
</script>
