import React from 'react'

export default function Header({title, subtitle}){
  return (
    <header>
      <div className="title-wrap">
        <h1>{title}</h1>
        <div className="title-underline" aria-hidden="true" />
      </div>
      {subtitle && <p className="subtitle">{subtitle}</p>}
    </header>
  )
}
