<template>
  <div>
    <div
      class="px-5 py-3 text-lg fixed z-20 bottom-3 right-3 text-gray-200 transition-transform transform shadow-2xl cursor-pointer rounded-15px bg-primary font-poppins hover:-translate-x-0.5 hover:-translate-y-0.5 hover:shadow-inner"
      @click="openCreateFolderPrompt()"
    >
      New Folder
    </div>
    <div v-if="showPrompt" class="absolute inset-0 flex">
      <form
        class="relative flex flex-col p-4 m-auto space-y-2 cursor-pointer bg-warm-gray-700 md:p-6 rounded-15px"
        @submit.prevent="createFolder()"
      >
        <ig-icon
          name="x"
          no-size
          variant="secondary"
          class="absolute h-6 top-2 right-2 md:h-8"
          @click.native="closeCreateFolderPrompt()"
        ></ig-icon>
        <base-input-box
          v-model="title"
          name="folder-title"
          placeholder-text="Folder title"
          no-hide-label
          focus-on-render
          class="text-warm-gray-200"
        ></base-input-box>
        <base-input-box
          v-model="description"
          name="folder-description"
          placeholder-text="Folder description"
          no-hide-label
          class="text-warm-gray-200"
          not-required
        ></base-input-box>
        <base-loading-button type="submit" text="Submit"></base-loading-button>
      </form>
    </div>
  </div>
</template>

<script>
import { sleep } from '~/utils/sleep.js'

export default {
  data() {
    return {
      showPrompt: false,
      title: '',
      description: '',
    }
  },
  methods: {
    openCreateFolderPrompt() {
      this.showPrompt = true
    },
    closeCreateFolderPrompt() {
      this.showPrompt = false
    },
    createFolder() {
      this.$axios
        .$post(`/folders/create`, {
          title: this.title,
          description: this.description,
        })
        .then(async () => {
          // I am not really sure why we need this sleep here, but we do. I do have a possible
          // explanation for it. When we send the POST request above, it takes some time for
          // the database to actually process the request and insert stuff into the database.
          // However, we were not really giving it much time to complete the insertion before
          // we were refreshing the list of folders from the database, resulting in it sending
          // us back an incomplete list of folders.
          await sleep(200)
          this.$emit('refreshFolders')
        })
        .catch((error) => {
          this.$addAlert({
            message: error.response.data,
            type: 'danger',
          })
        })
      this.title = ''
      this.description = ''
      this.closeCreateFolderPrompt()
    },
  },
}
</script>
