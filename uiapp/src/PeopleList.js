import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class PeopleList extends Component {

  constructor(props) {
    super(props);
    this.state = {people: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/people')
      .then(response => response.json())
      .then(data => this.setState({people: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/person/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedPeople = [...this.state.people].filter(i => i.id !== id);
      this.setState({people: updatedPeople});
    });
  }
    async removeAddress(id) {
    await fetch(`api/address/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
        window.location.reload();
    });
  }


  render() {
    const {people, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const peopleList = people.map(people => {

      return <tr key={people.id}>
        <td style={{whiteSpace: 'nowrap'}}>{people.name}</td>
        <td style={{whiteSpace: 'nowrap'}}>{people.surname}</td>
        <td>
        {people.addresses.map(address => {
          return <div key={address.id} >{address.street} {address.city} {address.state} {address.postalCode}
          <div>
             <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/address/" + address.id+"/"+people.id}>Edit Address</Button>
            <Button size="sm" color="primary" color="danger" onClick={() => this.removeAddress(address.id)}>Delete Address</Button>
          </ButtonGroup>
          </div>
          </div>
        })}
        </td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/person/" + people.id}>Edit Person</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(people.id)}>Delete Person</Button>
            <Button size="sm" color="success" tag={Link} to={"/address/new/"+people.id}>Add Address</Button>
          </ButtonGroup>
        </td>
      </tr>

    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/person/new">Add People</Button>
          </div>
          <h3>People Count: {peopleList.length}</h3>
          
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Surname</th>
              <th>Addresses</th>
              <th>Actions</th>  
            </tr>
            </thead>
            <tbody>
            {peopleList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default PeopleList;