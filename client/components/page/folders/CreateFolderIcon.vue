<template>
  <div>
    <div
      class="rounded-full shadow-2xl cursor-pointer bg-steel-300 ring ring-denim-700"
      @click="openCreateFolderPrompt()"
    >
      <ig-icon
        name="plus"
        no-size
        variant="black"
        class="h-10 md:h-14"
      ></ig-icon>
    </div>
    <div v-if="showPrompt" class="fixed inset-0 flex">
      <form
        class="relative flex flex-col p-4 m-auto bg-blue-500 cursor-pointer md:p-6 rounded-15px"
        @submit.prevent="createFolder()"
      >
        <ig-icon
          name="x"
          no-size
          variant="black"
          class="absolute h-6 top-1 right-1 md:h-8"
          @click.native="closeCreateFolderPrompt()"
        ></ig-icon>
        <base-input-box
          v-model="title"
          name="folder-title"
          placeholder-text="Folder title"
          no-hide-label
        ></base-input-box>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showPrompt: false,
      title: '',
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
      this.$axios.$post(`/folders/create/${this.$route.params.userId}`, {
        title: this.title,
      })
      this.closeCreateFolderPrompt()
      this.$emit('refreshFolders')
    },
  },
}
</script>
