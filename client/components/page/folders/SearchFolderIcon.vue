<template>
  <div>
    <div
      class="px-5 py-3 text-lg fixed z-20 bottom-3 left-3 sm:ml-64 text-gray-200 transition-transform transform shadow-2xl cursor-pointer rounded-15px bg-primary font-poppins hover:translate-x-0.5 hover:-translate-y-0.5 hover:shadow-inner"
      @click="searchFolderPrompt()"
    >
      Search
    </div>
    <div v-if="showPrompt" class="absolute inset-0 flex">
      <form
        class="relative flex flex-col p-4 mx-4 my-auto space-y-2 cursor-pointer bg-warm-gray-700 md:p-6 rounded-15px sm:mx-auto"
        @submit.prevent="searchFolder()"
      >
        <XIcon
          variant="secondary"
          class="absolute h-6 text-gray-100 top-2 right-2 md:h-8"
          @click="closeSearchFolderPrompt()"
        ></XIcon>
        <base-input-box
          v-model="tags"
          name="folder-tags"
          placeholder-text="Search by tags"
          no-hide-label
          focus-on-render
          class="text-warm-gray-200"
        ></base-input-box>
        <base-loading-button type="submit" text="Submit"></base-loading-button>
      </form>
    </div>
  </div>
</template>

<script>
import { XIcon } from 'vue-feather-icons'

export default {
  components: { XIcon },
  data() {
    return {
      showPrompt: false,
      tags: '',
    }
  },
  methods: {
    searchFolderPrompt() {
      this.showPrompt = true
    },
    closeSearchFolderPrompt() {
      this.showPrompt = false
    },
    searchFolder() {
      this.$emit('searchFolders', this.tags)
      this.tags = ''
      this.closeSearchFolderPrompt()
    },
  },
}
</script>
