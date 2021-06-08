<template>
  <form
    class="flex flex-col items-center justify-center w-full min-h-full px-5 mx-auto space-y-5 lg:w-2/3 lg:px-0"
    @submit.prevent="handleSubmit"
  >
    <div>
      <h1 class="text-4xl font-semibold font-poppins lg:tracking-wider">
        Create discussion
      </h1>
      <h3 class="lg:ml-2">
        <span class="text-primary">Have an invite link?</span>
        <NuxtLink
          :to="{ name: 'index' }"
          class="text-xl text-purple-800 underline font-poppins md:no-underline md:hover:underline"
          ><strong>Join existing</strong></NuxtLink
        >
      </h3>
    </div>
    <base-input-box
      v-model="formData.name"
      name="name"
      placeholder-text="Name"
      class="w-full lg:pt-5 lg:w-10/12"
      focus-on-render
      no-hide-label
    ></base-input-box>
    <base-input-box
      v-model="formData.description"
      name="description"
      placeholder-text="Description"
      class="w-full lg:w-10/12"
      textarea
      no-hide-label
    ></base-input-box>
    <div class="w-full lg:w-10/12">
      <page-socials-discussions-create-usernames
        :label="`Participants: ${users.length}`"
        @handleSelect="handleSelect"
      ></page-socials-discussions-create-usernames>
    </div>
    <div v-if="users.length > 0" class="w-11/12 lg:w-9/12">
      <div class="text-center text-primary lg:hidden">
        Selected Users (click to delete)
      </div>
      <div class="flex flex-wrap space-x-2">
        <img
          v-for="user in users"
          :key="user.id"
          v-tippy
          :src="user.imageURL"
          :content="getToolTip(user.id)"
          class="w-10 h-10 cursor-pointer rounded-3xl ring ring-emerald-500 hover:ring-rose-500"
          @click="deleteUser(user.id)"
        />
      </div>
    </div>
    <base-loading-button
      type="submit"
      text="Submit"
      :loading="loading"
      class="w-full lg:w-10/12"
    ></base-loading-button>
  </form>
</template>

<script>
import { sleep } from '~/utils/sleep'
export default {
  data() {
    return {
      users: [],
      formData: {
        name: '',
        description: '',
      },
      loading: false,
    }
  },
  computed: {
    formUsers() {
      return this.formData.users
    },
    userIDs() {
      return this.users.map((u) => String(u.id)).join(',')
    },
  },
  methods: {
    handleSelect(newVal) {
      const index = this.users.findIndex((x) => x.id === newVal.id)
      if (index === -1) this.users.push(newVal)
    },
    deleteUser(id) {
      const index = this.users.findIndex((x) => x.id === id)
      this.users.splice(index, 1)
    },
    getToolTip(id) {
      let template = '<ol style="text-align: left">'
      const index = this.users.findIndex((x) => x.id === id)
      template += `
        <li>Username: ${this.users[index].username}</li>
        <li style="color: #fc493d">Click to delete</li>
      `
      template += '</ol>'
      return template
    },
    async handleSubmit() {
      const data = { ...this.formData }
      this.loading = true
      try {
        const resp = await this.$axios.$post('/discussions/create', {
          name: data.name,
          description: data.description,
          userIDs: this.userIDs,
        })
        await sleep(1000)
        this.$router.push({
          name: 'socials-discussions-name',
          params: { name: resp.name },
        })
      } catch {
        this.$addAlert({
          message: 'A discussion with that name exists',
          type: 'info',
        })
      } finally {
        this.loading = false
      }
    },
  },
}
</script>
