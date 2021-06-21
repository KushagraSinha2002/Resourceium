<template>
  <div class="flex items-center justify-center p-6 bg-cream-white">
    <div
      class="flex flex-col px-8 py-4 mt-24 space-y-6 bg-light-black md:mt-0 rounded-15px md:w-11/12 lg:w-2/3"
    >
      <div
        class="pb-2 text-3xl border-b-2 border-gray-100 rounded-b-sm md:text-4xl text-steel-100 font-poppins"
      >
        Account Details
      </div>
      <div class="flex flex-col px-1 space-y-4 md:px-4">
        <page-profile-data-holder
          v-for="(value, name, index) in formData"
          :key="index"
          :label="name"
          :identifier="value"
          @changeData="changeData"
        ></page-profile-data-holder>
      </div>
      <base-loading-button
        :disabled="!changed"
        :loading="loading"
        text="Save"
        @click.native="submitForm"
      ></base-loading-button>
    </div>
  </div>
</template>

<script>
import localizedFormat from 'dayjs/plugin/localizedFormat'

export default {
  middleware: ['auth'],
  data() {
    return {
      changed: false,
      loading: false,
      formData: {
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        country: '',
        email: '',
        id: '',
        bio: '',
        username: '',
      },
    }
  },
  head: () => ({ title: 'Profile' }),
  mounted() {
    this.$dayjs.extend(localizedFormat)
    const user = this.$auth.user
    this.formData = {
      ...user,
      dateOfBirth: this.$dayjs(user.dateOfBirth).format('ll'),
      bio: user.bio ? user.bio : '',
    }
    delete this.formData.imageURL
    delete this.formData.documentCount
    delete this.formData.password
  },
  methods: {
    changeData(label, newData) {
      this.formData[label] = newData
      this.changed = true
    },
    async submitForm() {
      if (!this.changed) {
        return
      }
      const date = this.$dayjs(this.formData.dateOfBirth)
      if (!date.isValid()) {
        alert("Invalid date format. Should follow format: 'Apr 25, 2021'")
        return
      }
      this.formData.dateOfBirth = date.format('YYYY-MM-DD')
      this.loading = true
      await this.$axios.$put('/accounts/update', this.formData).then((resp) => {
        this.$auth.setUser(resp)
        window.location.reload()
      })
      this.loading = false
    },
  },
}
</script>
