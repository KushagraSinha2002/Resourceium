<template>
  <div class="flex items-center justify-center p-6 bg-cream-white">
    <div
      class="flex flex-col px-8 py-4 mt-24 space-y-6 md:mt-0 bg-primary rounded-15px md:w-2/3"
    >
      <div
        class="pb-2 text-3xl border-b-2 border-gray-100 rounded-b-sm md:text-4xl text-steel-100 font-poppins"
      >
        Account Details
      </div>
      <div class="flex flex-col px-1 space-y-4 md:px-4">
        <page-profile-data-holder
          label="Email I.D."
          :identifier="$auth.user.email"
        ></page-profile-data-holder>
        <page-profile-data-holder
          label="First Name"
          editable
          :identifier="$auth.user.firstName"
        ></page-profile-data-holder>
        <page-profile-data-holder
          label="Last Name"
          editable
          :identifier="$auth.user.lastName"
        ></page-profile-data-holder>
        <page-profile-data-holder
          label="User I.D."
          :identifier="$auth.user.id"
        ></page-profile-data-holder>
        <page-profile-data-holder
          label="Username"
          :identifier="$auth.user.username"
        ></page-profile-data-holder>
        <page-profile-data-holder
          label="D.O.B"
          editable
          :identifier="getFormattedDate($auth.user.dateOfBirth)"
        ></page-profile-data-holder>
        <page-profile-data-holder
          label="Country"
          editable
          :identifier="$auth.user.country"
        ></page-profile-data-holder>
        <page-profile-data-holder
          label="Password"
          identifier="********"
        ></page-profile-data-holder>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  middleware: ['auth'],
  data() {
    return {
      editMode: {
        firstName: false,
        lastName: false,
        dateOfBirth: false,
        country: false,
      },
      formData: {
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        country: '',
      },
    }
  },
  methods: {
    getFormattedDate(dateString) {
      return this.$moment(dateString).format('MMMM Do YYYY')
    },
  },
}
</script>
